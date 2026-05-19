package com.usnik.order_service.mapper;

import com.usnik.order_service.dto.OrderLineItemsRequest;
import com.usnik.order_service.dto.OrderRequest;
import com.usnik.order_service.dto.OrderingPair;
import com.usnik.order_service.model.Order;
import com.usnik.order_service.model.OrderLineItems;

import java.util.UUID;

public class OrderMapper {
	public static Order toOrderEntity(OrderRequest orderRequest) {
		return Order.builder()
				.orderNumber(UUID.randomUUID().toString())
				.orderLineItemsList(
						orderRequest.getOrderLineItemsListRequestDTO().stream().map(
								OrderMapper::toOrderLineItemsEntity
						).toList()
				).build();
	}

	public static OrderLineItems toOrderLineItemsEntity(OrderLineItemsRequest orderLineItemsrequest) {
		return OrderLineItems.builder()
				.skuCode(orderLineItemsrequest.getSkuCode())
				.quantity(orderLineItemsrequest.getQuantity())
				.price(orderLineItemsrequest.getPrice())
				.build();
	}

	public static OrderingPair toOrderingPairEntity(OrderLineItems orderLineItems) {
		return OrderingPair.builder().orderSku(orderLineItems.getSkuCode()).orderQuantity(orderLineItems.getQuantity().toString()).build();
	}

}
