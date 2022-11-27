package com.eworld.dto;
import java.util.List;

import com.eworld.contstant.CategoryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
	
	private Integer id;
	
	private String name;
	
	private String logo;
	
	private CategoryStatus status;
	
	private List<ProductDto> products;
}
