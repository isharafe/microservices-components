package com.ms.inventoryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.inventoryservice.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	boolean existsBySkuCode(String skuCode);

	boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, int quantity);

	List<Inventory> findBySkuCodeIn(Iterable<String> skuCodes);

}
