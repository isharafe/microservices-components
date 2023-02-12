package com.ms.productservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ms.productservice.model.Product;
import com.ms.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;

	public void createProduct(Product product) {
		productRepository.save(product);
		log.info("Product {} is saved", product.getId());
	}

	public Page<Product> getProducts(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
}
