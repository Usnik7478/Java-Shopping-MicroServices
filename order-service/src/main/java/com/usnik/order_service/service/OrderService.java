package com.usnik.order_service.service;

import com.usnik.order_service.dto.InventoryOrderingDTO;
import com.usnik.order_service.dto.OrderRequest;
import com.usnik.order_service.dto.OrderingPair;
import com.usnik.order_service.mapper.OrderMapper;
import com.usnik.order_service.model.Order;
import com.usnik.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	private final WebClient.Builder webClientBuilder;

	private final KafkaTemplate<String, InventoryOrderingDTO> kafkaTemplate;

	public String placeOrder(OrderRequest orderRequest) {
		try {
			Order order = OrderMapper.toOrderEntity(orderRequest);

			List<OrderingPair> skus = order.getOrderLineItemsList().stream()
					.map(OrderMapper :: toOrderingPairEntity).toList();

			log.info("Checking inventory for order with {} items", skus.size());

			String inventoryCheckResponse = webClientBuilder.build().post().uri("http://inventory-service/api/inventory/checkInventory")
					.bodyValue(InventoryOrderingDTO.builder().skuAndQuantities(skus).build())
					.retrieve()
					.bodyToMono(String.class)
					.block();

			if (!"allInInventory".equals(inventoryCheckResponse)) {
				log.warn("Some items are not in stock: {}", inventoryCheckResponse);
				return "Following items are currently not in stock :\n" + inventoryCheckResponse;
			}

			log.info("All items are in inventory. Processing order...");

			// Save order to database
			Order savedOrder = orderRepository.save(order);
//			log.info("Order saved with ID: {}", savedOrder.getId());

			// Send Kafka message for asynchronous inventory reduction
			try {
				CompletableFuture<SendResult<String, InventoryOrderingDTO>> v = kafkaTemplate.send("order_placed_inventory_update", InventoryOrderingDTO.builder().skuAndQuantities(skus).build());

				return "Order Placed Successfully. Order ID: " + savedOrder.getId();
			} catch (Exception kafkaException) {
				log.error("Failed to send Kafka message for order ID: {}", savedOrder.getId(), kafkaException);
				throw new RuntimeException("Failed to process order after saving", kafkaException);
			}

		} catch (Exception e) {
			log.error("Error placing order: {}", e.getMessage(), e);
			throw new RuntimeException("Error placing order", e);
		}
	}

}