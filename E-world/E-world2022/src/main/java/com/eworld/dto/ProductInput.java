package com.eworld.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.eworld.contstant.ProductStatus;
import com.eworld.entity.ImagesProduct;

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
	
	private Integer categoryId;
	
	private Date createdAt;
	
	private String name;
	
	private Double price;
	
	private int quantity;
	
	private String description;
	
	private String logo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaybaohanh;
	
	private ProductStatus status;
	
	private String urlVideo;
	
	private Integer models;
	
	private Set<ImagesProduct> imagesIds = new HashSet<>();
}
