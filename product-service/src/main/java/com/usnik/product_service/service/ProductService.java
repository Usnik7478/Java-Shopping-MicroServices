package com.usnik.product_service.service;

import com.usnik.product_service.dto.ProductRequest;
import com.usnik.product_service.dto.ProductResponse;
import com.usnik.product_service.mapper.ProductMapper;
import com.usnik.product_service.model.Product;
import com.usnik.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

		productRepository.save(product);

		log.info("Product {} has been created", product.getId());
	}

	public List<ProductResponse> getAllProducts() {

		return productRepository.findAll().stream().map(
				ProductMapper::toProductResponse
		).toList();
	}
}
