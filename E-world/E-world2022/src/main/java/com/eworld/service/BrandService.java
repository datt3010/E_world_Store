package com.eworld.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.dto.BrandDto;
import com.eworld.filter.BrandFilter;

public  interface BrandService {
	
	public Page<BrandDto> findPaging(BrandFilter filter, Pageable pageable);

}
