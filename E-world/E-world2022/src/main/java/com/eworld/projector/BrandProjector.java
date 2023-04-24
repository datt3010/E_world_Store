package com.eworld.projector;

import com.eworld.dto.brand.BrandDto;
import com.eworld.entity.Brand;

import java.util.List;
import java.util.stream.Collectors;

public class BrandProjector {
	
	public static List<BrandDto> convertToPageDto(List<Brand> entities){
		return entities.stream()
				.map(e ->  convertToDetailDto(e))
				.collect(Collectors.toList());
	}

		public static BrandDto convertToDetailDto(Brand entity) {
			return BrandDto.builder()
					.id(entity.getId())
					.name(entity.getName())
					.logo(entity.getLogo())
					.build();
		}
}
