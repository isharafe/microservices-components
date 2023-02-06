package com.ms.inventoryservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.inventoryservice.dto.InventoryResponse;
import com.ms.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public boolean isInStock(String skuCode) {
		return inventoryRepository.existsBySkuCode(skuCode);
	}

	@Transactional(readOnly = true)
	public boolean isInStock(String skuCode, int quantity) {
		return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
	}

	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(Map<String, Integer> lineQuantities) {
		Iterable<String> skuCodes = lineQuantities.keySet();
		return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
				.map(inv ->
					InventoryResponse.builder()
					.skuCode(inv.getSkuCode())
					.isInStock(inv.getQuantity() >= lineQuantities.get(inv.getSkuCode()))
					.build())
				.toList();
	}
}
