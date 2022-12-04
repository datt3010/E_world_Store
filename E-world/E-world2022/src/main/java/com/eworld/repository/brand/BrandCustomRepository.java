package com.eworld.repository.brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.entity.Brand;
import com.eworld.filter.BrandFilter;

public interface BrandCustomRepository {
	
	Page<Brand> findPaging(BrandFilter filter, Pageable pageable);

}
