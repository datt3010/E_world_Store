package com.eworld.projector;

import java.util.List;
import java.util.stream.Collectors;

import com.eworld.dto.order.OrderDetailDto;
import com.eworld.dto.order.OrderDto;
import com.eworld.entity.OrderDetail;

public class OrderDetailProjector {
	
	
	public static OrderDetailDto convertToPageDto(OrderDetail entity) {
		return OrderDetailDto.builder()
				.id(entity.getId())
				.quantity(entity.getQuantity())
				.productPrice(entity.getProductPrice())
				.build();
		
				
	}
	
	public static List<OrderDetailDto> convertToPageDto(List<OrderDetail> entities){
		return entities.stream()
				.map(e -> convertToPageDto(e))
				.collect(Collectors.toList());
	}
	
	public static OrderDetailDto convertToDetailDto(OrderDetail entity) {
		return OrderDetailDto.builder()
				.id(entity.getId())
				.quantity(entity.getQuantity())
				.productPrice(entity.getProductPrice())
				.build();
				
	}
}
