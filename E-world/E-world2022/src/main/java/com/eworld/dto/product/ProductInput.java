package com.eworld.dto.product;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.eworld.contstant.ProductStatus;

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
	
	@NotBlank(message = "{Product.name}")
	@Size(min = 4, max = 100, message = "{Size.product.name}" )
	private String name;
	
	@NotNull(message = "price is not empty")
	private Double price;
	
	@NotNull(message = "{Product.quantity}")
	private int quantity;
	
	private String description;
	
	private String logo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "{Product.ngaybaohanh}")
	private Date ngaybaohanh;

	@NotNull(message = "{Product.status}")
	private ProductStatus status;
	
	@NotBlank(message = "urlVideo is not empty")
	private String urlVideo;
	
	@NotNull(message = "{Product.models}")
	private Integer models;
	
}
