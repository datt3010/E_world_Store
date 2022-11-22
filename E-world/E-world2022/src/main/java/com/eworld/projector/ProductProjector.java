package com.eworld.projector;

import java.util.List;
import java.util.stream.Collectors;

import com.eworld.dto.CategoryDto;
import com.eworld.dto.ProductDto;
import com.eworld.entity.Product;

public class ProductProjector {
	
	public static ProductDto convertToPageDto(Product entity) {
		return ProductDto.builder()
				.createdAt(entity.getCreatedAt())
				.id(entity.getId())
				.name(entity.getName())
				.quantity(entity.getQuantity())
				.models(entity.getModel())
				.description(entity.getDescription())
				.urlVideo(entity.getUrlVideo())
				.ngayBaoHanh(entity.getNgaybaohanh())
				.status(entity.getStatus())
				.category(CategoryDto.builder()
						.id(entity.getCategory().getId())
						.name(entity.getCategory().getName())
						.logo(entity.getCategory().getLogo())
						.status(entity.getCategory().getStatus())
						.build())
				.build();
					
	}
	
	public static List<ProductDto> convertToPageDto(List<Product> entities){
		return entities.stream()
				.map(e -> convertToPageDto(e))
				.collect(Collectors.toList());
				
	}
	
	
}
