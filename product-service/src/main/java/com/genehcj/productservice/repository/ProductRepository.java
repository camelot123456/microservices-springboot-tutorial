package com.genehcj.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.genehcj.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
