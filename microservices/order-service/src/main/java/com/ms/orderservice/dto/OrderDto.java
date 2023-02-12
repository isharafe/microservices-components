package com.ms.orderservice.dto;

import java.util.List;

import com.ms.orderservice.model.OrderLineItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	private Long id;
	private String orderNo;
	private List<OrderLineItem> orderLineItems;

}
