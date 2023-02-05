package com.ms.inventoryservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ms.inventoryservice.model.Inventory;
import com.ms.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryBootstrapData implements CommandLineRunner {

	private final InventoryRepository inventoryRepository;

	@Override
	public void run(String... args) throws Exception {
		if(!inventoryRepository.existsBySkuCode("btsrtp-prd-1")) {
			Inventory inv1 = new Inventory();
			inv1.setSkuCode("btsrtp-prd-1");
			inv1.setQuantity(100);

			inventoryRepository.save(inv1);
		}

		if(!inventoryRepository.existsBySkuCode("btsrtp-prd-2")) {
			Inventory inv2 = new Inventory();
			inv2.setSkuCode("btsrtp-prd-2");
			inv2.setQuantity(0);


			inventoryRepository.save(inv2);
		}
	}

}
