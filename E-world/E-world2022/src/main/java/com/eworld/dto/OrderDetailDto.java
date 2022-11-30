package com.eworld.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDto {
	
	private Long id;
	
	private Integer quantity;
	
	private Double productPrice;
	
	private CartDto cart;

	private OrderDto order;
}
