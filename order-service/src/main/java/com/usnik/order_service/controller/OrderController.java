package com.usnik.order_service.controller;

import com.usnik.order_service.dto.OrderRequest;
import com.usnik.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/placeorder")
	@ResponseStatus(HttpStatus.CREATED)
	public String placeOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.placeOrder(orderRequest);
	}
}
