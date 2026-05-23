package com.usnik.order_service.service;

import com.usnik.order_service.dto.InventoryOrderingDTO;
import com.usnik.order_service.dto.InventoryResponseDTO;
import com.usnik.order_service.dto.OrderRequest;
import com.usnik.order_service.dto.OrderingPair;
import com.usnik.order_service.mapper.OrderMapper;
import com.usnik.order_service.model.Order;
import com.usnik.order_service.repository.OrderRepository;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	private final WebClient.Builder webClientBuilder;

	public String placeOrder(@RequestBody OrderRequest orderRequest) {

		Order order = OrderMapper.toOrderEntity(orderRequest);

		List<OrderingPair> skus = order.getOrderLineItemsList().stream()
				.map(OrderMapper :: toOrderingPairEntity).toList();

		String inventoryCheckResponse = webClientBuilder.build().post().uri("http://inventory-service/api/inventory/checkInventory")
				.bodyValue(InventoryOrderingDTO.builder().skuAndQuantities(skus).build())
				.retrieve()
				.bodyToMono(String.class)
				.block();

		if ("allInInventory".equals(inventoryCheckResponse)) {

			skus = order.getOrderLineItemsList().stream()
					.map(OrderMapper :: toOrderingPairEntity).toList();

			String result = webClientBuilder.build().post().uri("http://inventory-service/api/inventory/reduceFromInventory")
							.bodyValue(InventoryOrderingDTO.builder().skuAndQuantities(skus).build())
							.retrieve()
							.bodyToMono(String.class)
							.block();

			if(result == null){
				throw new IllegalStateException("Failed to receive response from inventory service");
			}
			if(result.equals("Error")){
				throw new TransactionalException("Unable to Place Order at this moment", null);
			}

			orderRepository.save(order);
			return "Order Placed";
		}
		else{
			return "Following items are currently not in stock :\n" + inventoryCheckResponse;
		}

	}

}
