package com.eworld.filter;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Setter
public class ProductFilter {
	
	private String keyword;

	private Integer categoryId;

	private  Integer brandId;
}
