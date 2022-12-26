package com.eworld.projector;

import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.product.ProductDto;
import com.eworld.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductProjector {
	
	public static ProductDto convertToPageDto(Product entity) {
		return ProductDto.builder()
				.createdAt(entity.getCreatedAt())
				.id(entity.getId())
				.name(entity.getName())
				.quantity(entity.getQuantity())
				.models(entity.getModels())
				.description(entity.getDescription())
				.urlVideo(entity.getUrlVideo())
				.ngaybaohanh(entity.getNgaybaohanh())
				.price(entity.getPrice())
				.status(entity.getStatus())
				.logo(entity.getImage())
				.category(CategoryDto.builder()
						.id(entity.getCategory().getId())
						.name(entity.getCategory().getName())
						.logo(entity.getCategory().getLogo())
						.status(entity.getCategory().getStatus())
						.build())
				.orderDetails(entity.getOrderDetails())
				.build();
					
	}
	
	public static List<ProductDto> convertToPageDto(List<Product> entities){
		return entities.stream()
				.map(e -> convertToPageDto(e))
				.collect(Collectors.toList());
				
	}
	
	public static ProductDto convertToDetailDto(Product entity) {
			
		List<ProductDto> listProductDto = entity.getCategory().getProducts().stream()
				.map(e -> ProductDto.builder()
						.id(e.getId())
						.name(e.getName())
						.price(e.getPrice())
						.quantity(e.getQuantity())
						.logo(e.getImage())
						.build())
				.collect(Collectors.toList());
		
			return ProductDto.builder()
					.id(entity.getId())
					.name(entity.getName())
					.price(entity.getPrice())
					.quantity(entity.getQuantity())
					.models(entity.getModels())
					.quantity(entity.getQuantity())
					.description(entity.getDescription())
					.urlVideo(entity.getUrlVideo())
					.ngaybaohanh(entity.getNgaybaohanh())
					.logo(entity.getImage())
					.status(entity.getStatus())
					.category(CategoryDto.builder()
						.id(entity.getCategory().getId())
						.name(entity.getCategory().getName())
						.products(listProductDto)
						.build())
					.build();
	}
}
