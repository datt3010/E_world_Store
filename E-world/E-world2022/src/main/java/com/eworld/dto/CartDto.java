package com.eworld.dto;

import com.eworld.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	
	private Integer quantity;
	
	private Product product;
}
