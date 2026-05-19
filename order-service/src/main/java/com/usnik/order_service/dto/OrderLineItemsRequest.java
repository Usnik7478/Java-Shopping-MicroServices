package com.usnik.order_service.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class OrderLineItemsRequest {
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
}
