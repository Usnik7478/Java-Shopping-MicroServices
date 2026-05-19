package com.usnik.product_service.mapper;

import com.usnik.product_service.dto.ProductResponse;
import com.usnik.product_service.model.Product;

public class ProductMapper {
	public static ProductResponse toProductResponse(Product product) {
		return ProductResponse.builder()
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
