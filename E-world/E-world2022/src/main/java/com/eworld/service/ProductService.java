package com.eworld.service;

import com.eworld.dto.category.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.dto.product.ProductDto;
import com.eworld.dto.product.ProductInput;
import com.eworld.dto.product.ProductUpdate;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;

public interface ProductService {
		
	public  ProductDto create(ProductInput input);
	
	public 	ProductDto update(Integer id, ProductUpdate input);

	public Page<ProductDto> findPaging(String keyword, String sortField, String sortDir, int pageNum);
		
	public void changeStatus(Integer id);
	
	public ProductDto getDetail(Integer id);
	
	public Product findbyId(Integer id);
		
}
