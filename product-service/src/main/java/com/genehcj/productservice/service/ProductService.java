package com.genehcj.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.genehcj.productservice.dto.ProductRequest;
import com.genehcj.productservice.model.Product;
import com.genehcj.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		product = productRepository.save(product);
		log.info("Product {} is saved", product.getId());
	}
	
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}
	
}
