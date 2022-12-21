package com.eworld.dto.brand;

import java.util.Set;

import com.eworld.dto.category.CategoryBrandDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandDto {
	
private Integer id;
	
	private String name;
	
	private String logo;
	
	private Set<CategoryBrandDto> categoryBrands;
}
