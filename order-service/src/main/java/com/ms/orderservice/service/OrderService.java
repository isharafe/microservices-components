package com.ms.orderservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.orderservice.model.Order;
import com.ms.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional /* spring will run methods in this class in a transaction */
public class OrderService {

	private final OrderRepository orderRepository;

	public void placeOrder(Order order) {
		order.setOrderNo(UUID.randomUUID().toString());
		orderRepository.save(order);
	}
}
