package com.ms.inventoryservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
