package com.usnik.order_service.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class OrderRequest {
	@OneToMany(cascade= CascadeType.ALL)
	private List<OrderLineItemsRequest> orderLineItemsListRequestDTO;
}
