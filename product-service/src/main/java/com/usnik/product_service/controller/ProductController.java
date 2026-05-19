package com.usnik.product_service.controller;

import com.usnik.product_service.dto.ProductRequest;
import com.usnik.product_service.dto.ProductResponse;
import com.usnik.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/createProduct")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void createProduct(@RequestBody ProductRequest productRequest) {
		productService.createProduct(productRequest);
	}

	@GetMapping("/getAllProducts")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<ProductResponse> getAllProducts() {
		return productService.getAllProducts();
	}
}
