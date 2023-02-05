package com.ms.orderservice.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.orderservice.dto.OrderDto;
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
	public Long placeOrder(@RequestBody OrderDto orderDto) {
		Order order = modelMapper.map(orderDto, Order.class);
		orderService.placeOrder(order);
		return order.getId();
	}
}
