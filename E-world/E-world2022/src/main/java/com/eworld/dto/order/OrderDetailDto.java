package com.eworld.dto.order;

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

	private OrderDto order;
}
