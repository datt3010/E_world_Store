package com.eworld.filter;

import com.eworld.contstant.OrderStatus;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Setter
public class OrderFilter {
	
	private String keyword;

	private Integer accountId;

	private OrderStatus status;
}
