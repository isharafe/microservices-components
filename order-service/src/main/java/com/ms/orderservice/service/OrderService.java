package com.ms.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ms.orderservice.dto.InventoryResponse;
import com.ms.orderservice.exception.InsufficientQuantityException;
import com.ms.orderservice.model.Order;
import com.ms.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional /* spring will run methods in this class in a transaction */
public class OrderService {

	private final OrderRepository orderRepository;
	private final WebClient webClient;

	public void placeOrder(Order order) throws InsufficientQuantityException {
		order.setOrderNo(UUID.randomUUID().toString());

		/*
		 * call inventory service and
		 * place order if product is in stock
		 */
		InventoryResponse[] inventoryResponses = webClient.get()
		.uri("http://localhost:8082/api/inventory/isInStock",
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
		} else {
			throw new InsufficientQuantityException(
					String.format("These product items are not in stock - %s. Please try again later.",
					noStockItems.stream()
					.map(InventoryResponse::getSkuCode)
					.collect(Collectors.joining())));
		}
	}
}
