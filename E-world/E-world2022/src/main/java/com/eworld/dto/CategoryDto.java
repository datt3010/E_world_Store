package com.eworld.dto;
import java.util.List;
import java.util.Set;
import com.eworld.contstant.CategoryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
	
	private Integer id;
	
	private String name;
	
	private String logo;
	
	private CategoryStatus status;
	
	private List<ProductDto> products;
	
	private Set<CategoryBrandDto> categoryBrands;
}
