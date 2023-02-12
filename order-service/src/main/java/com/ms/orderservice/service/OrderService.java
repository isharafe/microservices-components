package com.ms.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ms.orderservice.dto.InventoryResponse;
import com.ms.orderservice.event.OrderPlacedEvent;
import com.ms.orderservice.exception.InsufficientQuantityException;
import com.ms.orderservice.exception.InventoryServiceNotAvailableException;
import com.ms.orderservice.model.Order;
import com.ms.orderservice.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional /* spring will run methods in this class in a transaction */
public class OrderService {

	private static final String ORDER_PLACED_TOPIC = "order-placed-topic";

	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	private final Tracer tracer;
	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

	@CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
	@TimeLimiter(name = "inventory")
	@Retry(name = "inventory")
	/* COmpletableFuture is necessary for "TimeLimiter" annotation **/
	public CompletableFuture<Order> placeOrder(Order order) {

		order.setOrderNo(UUID.randomUUID().toString());

		return CompletableFuture.supplyAsync(() -> {

			Span orderQuantityLookup = tracer.nextSpan().name("order-quantity-lookup");

			try(Tracer.SpanInScope quantityLookup = tracer.withSpan(orderQuantityLookup.start())) {

				/*
				 * call inventory service and
				 * place order if product is in stock
				 */
				InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory/isInStock",
						uriBuilder -> {
							order.getOrderLineItems()
								.forEach(line -> uriBuilder.queryParam(line.getSkuCode(), line.getQuantity()));
							return uriBuilder.build();
						})
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();

				List<InventoryResponse> noStockItems = Arrays.stream(inventoryResponses)
						.filter(inventoryResponse -> !inventoryResponse.isInStock())
						.toList();

				if(noStockItems.isEmpty()) {
					orderRepository.save(order);

					kafkaTemplate.send(ORDER_PLACED_TOPIC, new OrderPlacedEvent(order.getOrderNo()));

					return order;
				} else {
					throw new InsufficientQuantityException(
							String.format("These product items are not in stock - %s. Please try again later.",
							noStockItems.stream()
							.map(InventoryResponse::getSkuCode)
							.collect(Collectors.joining())));
				}
			} finally {
				orderQuantityLookup.end();
			}
		});
	}

	/**
	 *
	 * @param order
	 * @param runtimeException - exception occurred in the above placeOrder method
	 */
	public CompletableFuture<Order> fallBackMethod (Order order, RuntimeException runtimeException) {
		throw new InventoryServiceNotAvailableException("Oops!");
	}
}
