package com.usnik.order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="t_order_line_items")
public class OrderLineItems {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
}
