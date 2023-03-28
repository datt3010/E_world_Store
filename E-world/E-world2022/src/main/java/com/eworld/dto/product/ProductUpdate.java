package com.eworld.dto.product;

import com.eworld.contstant.ProductStatus;
import com.eworld.entity.ProductImages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdate {
	
	private Date createdAt;

	private Date updatedAt;
	
	@NotBlank(message = "{Product.name}")
	@Size(min = 4, max = 100, message = "{Size.product.name}" )
	private String name;
	
	@NotNull(message = "{Product.price}")
	private Double price;
	
	@NotNull(message = "{Product.quantity}")
	private int quantity;
	
	private String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "{Product.ngaybaohanh}")
	private Date ngaybaohanh;
	
	@NotNull(message = "{Product.status}")
	private ProductStatus status;
	
	@NotBlank(message = "{Product.urlVideo}")
	private String urlVideo;

	private Integer categoryId;

	private Set<ProductImages> productImages;
	
	
}
