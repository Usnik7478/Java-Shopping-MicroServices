package com.example.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponseDTO {
	private String skuCode;
	private Boolean isInStock;

	public InventoryResponseDTO skuCode(String sk){
		this.skuCode = sk;
		return this;
	}

	public InventoryResponseDTO isInStock(Boolean is){
		this.isInStock = is;
		return this;
	}
}
