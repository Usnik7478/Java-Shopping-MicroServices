package com.example.inventory_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderingPair {
	public String orderSku;
	public String orderQuantity;
}
