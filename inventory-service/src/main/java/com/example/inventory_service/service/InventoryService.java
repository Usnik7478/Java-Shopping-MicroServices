package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryOrderingDTO;
import com.example.inventory_service.dto.InventoryResponseDTO;
import com.example.inventory_service.mapper.InventoryMapper;
import com.example.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public List<InventoryResponseDTO> isInInventory(List<String> skuCode){
		return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(
				InventoryMapper :: toInventoryIsInStockResponseDTO
		).toList();
	}

	public String reduceFromInventory(InventoryOrderingDTO inventoryOrderingDTO) {
		return inventoryOrderingDTO.getSkuAndQuantities().stream()
				.map(order -> inventoryRepository.updateInventoryByForOrderPlacing(order.getOrderSku(), Integer.parseInt(order.getOrderQuantity())))
				.toList().size() == inventoryOrderingDTO.getSkuAndQuantities().size() ? "All Done" : "Error";
	}
}
