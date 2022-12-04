package com.eworld.projector;

import java.util.List;
import java.util.stream.Collectors;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.order.OrderDto;
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
				.totalPrice(entity.getTotalPrice())
				.status(entity.getStatus())
				.createdAt(entity.getCreatedAt())
//				.account(CustomerDto.builder()
//						.//id(entity.getAccount().getId())
//						.firstName(entity.getAccount().getFirstName())
//						.lastName(entity.getAccount().getLastName())
//						.username(entity.getAccount().getUsername())
//						.build())
				.build();
	}

		public static OrderDto convertToDetailDto(Order entity) {
			return OrderDto.builder()
					.id(entity.getId())
					.address(entity.getAddress())
					.phone(entity.getPhone())
					.status(entity.getStatus())
					.createdAt(entity.getCreatedAt())
					.account(CustomerDto.builder()
							.id(entity.getAccount().getId())
							.firstName(entity.getAccount().getFirstName())
							.lastName(entity.getAccount().getLastName())
							.build())
					.totalPrice(entity.getTotalPrice())
					.build();
		}
}
