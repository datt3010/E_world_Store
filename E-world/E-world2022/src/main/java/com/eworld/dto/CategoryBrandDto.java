package com.eworld.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBrandDto {
	
	private Integer id;
	
	private Integer categoryId;
	
	private Integer brandId;
	
	private CategoryDto category;
	
	private BrandDto brand;
}
