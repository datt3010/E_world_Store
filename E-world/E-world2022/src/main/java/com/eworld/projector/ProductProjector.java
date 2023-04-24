package com.eworld.projector;

import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.product.ProductDto;
import com.eworld.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductProjector {

	public static List<ProductDto> convertToPageDto(List<Product> entities){
		return entities.stream()
				.map(e -> convertToDetailDto(e))
				.collect(Collectors.toList());
				
	}
	
	public static ProductDto convertToDetailDto(Product entity) {
			
		List<ProductDto> listProductDto = entity.getCategory().getProducts().stream()
				.map(e -> ProductDto.builder()
						.createdAt(entity.getCreatedAt())
						.updatedAt(entity.getUpdatedAt())
						.categoryId(entity.getCategoryId())
						.id(e.getId())
						.name(e.getName())
						.price(e.getPrice())
						.quantity(e.getQuantity())
						.productImages(e.getProductImages())
						.build())
				.collect(Collectors.toList());
		
			return ProductDto.builder()
					.id(entity.getId())
					.name(entity.getName())
					.price(entity.getPrice())
					.quantity(entity.getQuantity())
					.description(entity.getDescription())
					.urlVideo(entity.getUrlVideo())
					.ngaybaohanh(entity.getNgaybaohanh())
					.status(entity.getStatus())
					.views(entity.getViews())
					.productImages(entity.getProductImages())
					.orderDetails(entity.getOrderDetails())
					.category(CategoryDto.builder()
						.id(entity.getCategory().getId())
						.name(entity.getCategory().getName())
						.products(listProductDto)
						.build())
					.build();
	}
}
