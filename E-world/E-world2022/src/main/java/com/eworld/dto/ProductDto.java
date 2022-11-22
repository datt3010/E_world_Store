package com.eworld.dto;

import java.util.Date;
import java.util.Set;

import com.eworld.contstant.ProductStatus;
import com.eworld.entity.ImagesProduct;
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
	
	private String name;
	
	private Double price;
	
	private Integer quantity;
	
	private String urlVideo;
	
	private Date ngayBaoHanh;
	
	private Integer models;
	
	private String description;
	
	private ProductStatus status;
	
	private CategoryDto category;
	
	private Set<OrderDetail> orderDetails;
	
	private Set<ImagesProduct> imageProducts;
}
