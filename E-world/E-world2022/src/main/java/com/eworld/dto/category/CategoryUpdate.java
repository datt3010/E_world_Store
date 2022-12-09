package com.eworld.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eworld.contstant.CategoryStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryUpdate {
	
	@NotBlank(message = "{Size.Category.name}")
	@Size(max = 100)
	private String name;
	
	private String logo;
	
	@NotNull
	private CategoryStatus status;
}
