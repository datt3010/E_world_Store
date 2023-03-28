package com.eworld.dto.product;

import com.eworld.contstant.ProductStatus;
import com.eworld.dto.category.CategoryDto;
import com.eworld.entity.OrderDetail;
import com.eworld.entity.ProductImages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

	private Date createdAt;

	private Date updatedAt;

	private Integer id;
	
	private Integer categoryId;
	
	private String name;
	
	private Double price;

	private Integer views;
	
	private Integer quantity;
	
	private String urlVideo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaybaohanh;

	private String description;
	
	private ProductStatus status;
	
	private CategoryDto category;

	private Set<OrderDetail> orderDetails;

	private  Set<ProductImages> productImages;
}
