package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryOrderingDTO;
import com.example.inventory_service.dto.OrderingPair;
import com.example.inventory_service.model.Inventory;
import com.example.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public String isInInventory(InventoryOrderingDTO inventoryOrderingDTO){
		StringBuilder res = new StringBuilder();

		for(OrderingPair order : inventoryOrderingDTO.getSkuAndQuantities()){
			Inventory inv = inventoryRepository.checkInventory(order.getOrderSku(), Integer.parseInt(order.getOrderQuantity()));
			if(inv == null){
				res.append(order.getOrderSku()).append(": ").append(order.getOrderQuantity()).append("\n");
			}
		}

		if(res.isEmpty()){
			return "allInInventory";
		}
		return res.toString();
	}

	public String reduceFromInventory(InventoryOrderingDTO inventoryOrderingDTO) {
		return inventoryOrderingDTO.getSkuAndQuantities().stream()
				.map(order -> inventoryRepository.updateInventoryByForOrderPlacing(order.getOrderSku(), Integer.parseInt(order.getOrderQuantity())))
				.toList().size() == inventoryOrderingDTO.getSkuAndQuantities().size() ? "All Done" : "Error";
	}
}
