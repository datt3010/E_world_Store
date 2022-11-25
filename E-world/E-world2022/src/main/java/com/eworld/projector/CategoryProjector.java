package com.eworld.projector;
import java.util.List;
import java.util.stream.Collectors;

import com.eworld.dto.CategoryDto;
import com.eworld.entity.Category;

public class CategoryProjector {
	
	public static List<CategoryDto> convertToPageDto(List<Category> entities){
			return entities.stream()
					.map(e ->  convertToPageDto(e))
					.collect(Collectors.toList());
	}
	
	public static CategoryDto convertToPageDto(Category entity) {
		return CategoryDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.logo(entity.getLogo())
				.status(entity.getStatus())
				.build();
	}
	
	public static CategoryDto convertToDetailDto(Category entity) {
		
		return CategoryDto.builder()
				.name(entity.getName())
				.logo(entity.getLogo())
				.status(entity.getStatus())
				.build();
	}
}
