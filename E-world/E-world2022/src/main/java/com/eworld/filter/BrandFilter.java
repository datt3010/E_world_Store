package com.eworld.filter;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Setter
public class BrandFilter {
	
	private String keyword;
}