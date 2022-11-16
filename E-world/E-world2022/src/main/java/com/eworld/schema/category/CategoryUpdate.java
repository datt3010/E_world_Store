package com.eworld.schema.category;

import com.eworld.contstant.CategoryStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryUpdate {

	private String name;
	
	private String logo;
	
	private CategoryStatus status;
}
