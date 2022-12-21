package com.eworld.repository.product;

import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
	
	Page<Product> findPaging(ProductFilter filter, Pageable pageable);

}
