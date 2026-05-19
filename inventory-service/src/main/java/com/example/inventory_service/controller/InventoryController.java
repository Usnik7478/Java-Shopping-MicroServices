package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryOrderingDTO;
import com.example.inventory_service.dto.InventoryResponseDTO;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/checkInventory")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponseDTO> isInInventory(@RequestParam(value = "skuCode") List<String> skuCode){
		return inventoryService.isInInventory(skuCode);
	}

	@PostMapping("/reduceFromInventory")
	@ResponseStatus(HttpStatus.OK)
	public String reduceFromInventory(@RequestBody InventoryOrderingDTO  inventoryOrderingDTO){
		return inventoryService.reduceFromInventory(inventoryOrderingDTO);
	}
}
