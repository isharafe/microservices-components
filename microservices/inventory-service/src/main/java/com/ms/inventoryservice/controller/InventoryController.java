package com.ms.inventoryservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.inventoryservice.dto.InventoryResponse;
import com.ms.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/{sku-code}")
	@ResponseStatus(code = HttpStatus.OK)
	public boolean isInStock(@PathVariable("sku-code") String skuCode) {
		return inventoryService.isInStock(skuCode);
	}

	@GetMapping("/{sku-code}/{quantity}")
	@ResponseStatus(code = HttpStatus.OK)
	public boolean isInStock(@PathVariable("sku-code") String skuCode, @PathVariable Integer quantity) {
		return inventoryService.isInStock(skuCode, quantity);
	}

	@GetMapping("/isInStock")
	@ResponseStatus(code = HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam Map<String, String> reqValues) {
		// request values (quantities) are String, convert them to Integer before process
		Map<String, Integer> lineQuantites = reqValues.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, e -> Integer.parseInt(e.getValue())));
		return inventoryService.isInStock(lineQuantites);
	}
}
