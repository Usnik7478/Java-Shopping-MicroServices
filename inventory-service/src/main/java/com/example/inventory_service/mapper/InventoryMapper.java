package com.example.inventory_service.mapper;


import com.example.inventory_service.dto.InventoryResponseDTO;
import com.example.inventory_service.model.Inventory;

public class InventoryMapper {
	public static InventoryResponseDTO toInventoryIsInStockResponseDTO(Inventory inventory){
		return InventoryResponseDTO.builder().build()
				.skuCode(inventory.getSkuCode())
				.isInStock(inventory.getQuantity() > 0);
	}
}
