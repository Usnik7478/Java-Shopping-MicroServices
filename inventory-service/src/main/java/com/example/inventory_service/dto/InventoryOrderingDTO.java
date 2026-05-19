package com.example.inventory_service.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryOrderingDTO {
	public List<OrderingPair>skuAndQuantities;
}
