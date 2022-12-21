package com.eworld.service;

import com.eworld.dto.product.ProductDto;
import com.eworld.dto.product.ProductInput;
import com.eworld.dto.product.ProductUpdate;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
		
	public  ProductDto create(ProductInput input);
	
	public 	ProductDto update(Integer id, ProductUpdate input);

	public Page<ProductDto> findPaging(ProductFilter filter, Pageable pageable);
		
	public void changeStatus(Integer id);
	
	public ProductDto getDetail(Integer id);
	
	public ProductDto findbyId(Integer id);

	public Page<Product> findProductByCategoryId(Integer categoryId , Pageable pageable);

}
