package com.eworld.projector;

import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.product.ProductDto;
import com.eworld.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryProjector {
	
	public static List<CategoryDto> convertToPageDto(List<Category> entities){
			return entities.stream()
					.map(e ->  convertToDetailDto(e))
					.collect(Collectors.toList());
	}
	
	public static CategoryDto convertToDetailDto(Category entity) {
		
	List<ProductDto> products = entity.getProducts().stream()
			.map(e -> ProductDto.builder()
					.name(e.getName())
					.price(e.getPrice())
					.quantity(e.getQuantity())
					.build())
			.collect(Collectors.toList());
		
		return CategoryDto.builder()
				.name(entity.getName())
				.logo(entity.getLogo())
				.status(entity.getStatus())
				.products(products)
				.build();
	}
}
