package com.ms.productservice.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.productservice.dto.ProductDto;
import com.ms.productservice.model.Product;
import com.ms.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private static final String DEFALUT_PAGE_NUMBER = "0";
	private static final String DEFAULT_PAGE_SIZE = "100";

	private final ModelMapper mapper;
	private final ProductService productService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public String createProduct(
			@RequestBody ProductDto productRequest) {
		Product product = mapper.map(productRequest, Product.class);
		productService.createProduct(product);
		return product.getId();
	}

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Page<ProductDto> getProducts(
			@RequestParam(required = false, defaultValue = DEFALUT_PAGE_NUMBER) int page,
			@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> products = productService.getProducts(pageable);
		return products
				.map(p -> mapper.map(p, ProductDto.class));
	}

}
