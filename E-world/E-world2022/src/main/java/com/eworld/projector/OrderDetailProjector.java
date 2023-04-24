package com.eworld.projector;

import com.eworld.dto.order.OrderDetailDto;
import com.eworld.entity.OrderDetail;
import com.eworld.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailProjector {
	
	
	public static OrderDetailDto convertToPageDto(OrderDetail entity) {
		return OrderDetailDto.builder()
				.id(entity.getId())
				.quantity(entity.getQuantity())
				.productPrice(entity.getProductPrice())
				.product(Product.builder()
						.createdAt(entity.getProduct().getCreatedAt())
						.updatedAt(entity.getProduct().getUpdatedAt())
						.id(entity.getProduct().getId())
						.categoryId(entity.getProduct().getCategoryId())
						.name(entity.getProduct().getName())
						.price(entity.getProduct().getPrice())
						.quantity(entity.getProduct().getQuantity())
						.urlVideo(entity.getProduct().getUrlVideo())
						.ngaybaohanh(entity.getProduct().getNgaybaohanh())
						.views(entity.getProduct().getViews())
						.description(entity.getProduct().getDescription())
						.status(entity.getProduct().getStatus())
						.productImages(entity.getProduct().getProductImages())
						.build())
				.build();
		
				
	}
	
	public static List<OrderDetailDto> convertToPageDto(List<OrderDetail> entities){
		return entities.stream()
				.map(e -> convertToPageDto(e))
				.collect(Collectors.toList());
	}
}
