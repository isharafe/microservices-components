package com.ms.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ms.notification.event.OrderPlacedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

	private static final String ORDER_PLACED_TOPIC = "order-placed-topic";

	@KafkaListener(topics = {ORDER_PLACED_TOPIC})
	public void doNotify(OrderPlacedEvent event) {
		log.info("Received notification for order: {}", event.getOrderNo());
		// write logic to send an email or send a email
	}
}
