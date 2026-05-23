package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryOrderingDTO;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@PostMapping("/checkInventory")
	@ResponseStatus(HttpStatus.OK)
	public String isInInventory(@RequestBody InventoryOrderingDTO  inventoryOrderingDTO){
		return inventoryService.isInInventory(inventoryOrderingDTO);
	}

	@PostMapping("/reduceFromInventory")
	@ResponseStatus(HttpStatus.OK)
	public String reduceFromInventory(@RequestBody InventoryOrderingDTO  inventoryOrderingDTO){
		return inventoryService.reduceFromInventory(inventoryOrderingDTO);
	}
}
