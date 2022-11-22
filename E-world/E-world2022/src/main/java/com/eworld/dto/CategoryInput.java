package com.eworld.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eworld.contstant.CategoryStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class CategoryInput {
	
	private Integer id;
	
	@NotBlank(message = "{Size.Category.name}")
	@Size(max = 100)
	private String name;
	
	private String logo;
	
	@NotNull
	private CategoryStatus status;
}
