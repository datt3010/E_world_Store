package com.eworld.dto;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.eworld.contstant.ProductStatus;
import com.eworld.entity.OrderDetail;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
	
	private Date createdAt;
	
	private Integer id;
	
	private Integer categoryId;
	
	private String name;
	
	private Double price;
	
	private Integer quantity;
	
	private String urlVideo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaybaohanh;
	
	private Integer models;
	
	private String description;
	
	private ProductStatus status;
	
	private CategoryDto category;
	
	private String logo;
	
	private Set<OrderDetail> orderDetails;	
}
