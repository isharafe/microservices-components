package com.ms.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ms.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
