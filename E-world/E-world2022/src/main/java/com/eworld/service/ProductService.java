package com.eworld.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.dto.ProductDto;
import com.eworld.dto.ProductInput;
import com.eworld.dto.ProductUpdate;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;

public interface ProductService {
		
	public  ProductDto create(ProductInput input);
	
	public 	ProductDto update(Integer id, ProductUpdate input);
	
	public Page<ProductDto> findPaging(ProductFilter filter, Pageable pageable);
		
	public void deleteById(Integer id);
	
	public ProductDto getDetail(Integer id);
	
	public Product findbyId(Integer id);
	
}
