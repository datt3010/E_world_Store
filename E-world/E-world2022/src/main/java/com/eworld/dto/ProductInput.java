package com.eworld.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInput {
	
	private Integer id;
	
	private Date createdAt;
	
	private String name;
	
	private Double price;
	
	private int quantity;
	
	private String description;
	
	private String logo;
	
	private String ngaybaohanh;
	
}
