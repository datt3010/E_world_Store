package com.eworld.projector;

import java.util.List;
import java.util.stream.Collectors;

import com.eworld.dto.brand.BrandDto;
import com.eworld.entity.Brand;

public class BrandProjector {
	
	public static List<BrandDto> convertToPageDto(List<Brand> entities){
		return entities.stream()
				.map(e ->  convertToPageDto(e))
				.collect(Collectors.toList());
	}

	public static BrandDto convertToPageDto(Brand entity) {
//		
//			Set<CategoryBrandDto> categoryBrands = entity.getCategoryBrands().stream()
//				.map(e -> CategoryBrandDto.builder()
//						.category(CategoryDto.builder()
//								.name(e.getCategory().getName())
//								.build())
//						.build())
//				.collect(Collectors.toSet());
		
		return BrandDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.logo(entity.getLogo())
//				.categoryBrands(categoryBrands)
				.build();
	}

		public static BrandDto convertToDetailDto(Brand entity) {
			return BrandDto.builder()
					.name(entity.getName())
					.logo(entity.getLogo())
					.build();
		}
}
