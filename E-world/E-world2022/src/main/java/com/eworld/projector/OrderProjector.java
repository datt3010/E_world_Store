package com.eworld.projector;

import java.util.List;
import java.util.stream.Collectors;

import com.eworld.dto.OrderDto;
import com.eworld.entity.Order;

public class OrderProjector {
	
	public static List<OrderDto> convertToPageDto(List<Order> entities){
		return entities.stream()
				.map(e ->  convertToPageDto(e))
				.collect(Collectors.toList());
	}

	public static OrderDto convertToPageDto(Order entity) {
		return OrderDto.builder()
				.id(entity.getId())
				.address(entity.getAddress())
				.phone(entity.getPhone())
				.freightFee(entity.getFreightFee())				
				.build();
	}

		public static OrderDto convertToDetailDto(Order entity) {
			return OrderDto.builder()
					.id(entity.getId())
					.address(entity.getAddress())
					.phone(entity.getPhone())
					.freightFee(entity.getFreightFee())				
					.build();
		}
}
