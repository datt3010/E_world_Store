package com.eworld.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;

public interface ProductCustomRepository {
	
	Page<Product> findPaging(ProductFilter filter, Pageable pageable);

}
