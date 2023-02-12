package com.ms.orderservice.controller;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.orderservice.dto.OrderDto;
import com.ms.orderservice.exception.InsufficientQuantityException;
import com.ms.orderservice.model.Order;
import com.ms.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

	private final ModelMapper modelMapper;
	private final OrderService orderService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Long> placeOrder(@RequestBody OrderDto orderDto) throws InsufficientQuantityException {
		Order order = modelMapper.map(orderDto, Order.class);
		return orderService.placeOrder(order)
				.thenApply(Order::getId);
	}
}
