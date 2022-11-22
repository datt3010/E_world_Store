package com.eworld.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.contstant.ProductStatus;
import com.eworld.dto.CategoryInput;
import com.eworld.dto.CategoryUpdate;
import com.eworld.dto.ProductDto;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;

public interface ProductService {
	
	public List<Product> findAll(ProductStatus status);
	
	public  ProductDto create(CategoryInput input);
	
	public 	ProductDto update(Integer id, CategoryUpdate input);
	
	public Page<ProductDto> findPaging(ProductFilter filter, Pageable pageable);
		
	public void deleteById(Integer id);
	
	public ProductDto getDetail(Integer id);
}
