package com.example.inventory_service.consumers;

import com.example.inventory_service.dto.InventoryOrderingDTO;
import com.example.inventory_service.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryConsumer {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ObjectMapper objectMapper; // Spring provides this automatically

	@KafkaListener(
			topics = "order_placed_inventory_update",
			groupId = "inventory-service-group-v3"
	)
	public void reduceFromInventoryAsync(String rawJson, Acknowledgment ack) {
		try {
			InventoryOrderingDTO dto = objectMapper.readValue(rawJson, InventoryOrderingDTO.class);

			log.info("Received inventory order: {}", dto);
			String res = inventoryService.reduceFromInventory(dto);
			log.info("Inventory reduction response: {}", res);

			ack.acknowledge();
		} catch (Exception e) {
			log.error("Failed to deserialize or process message: {}", rawJson, e);
		}
	}
}