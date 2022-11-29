package com.eworld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eworld.dto.BrandDto;
import com.eworld.entity.Brand;
import com.eworld.filter.BrandFilter;
import com.eworld.projector.BrandProjector;
import com.eworld.repository.BrandRepository;
import com.eworld.service.BrandService;

@Service
@Transactional(readOnly = true)
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandRepository brandRepo;
	
	@Override
	public Page<BrandDto> findPaging(BrandFilter filter, Pageable pageable) {
		Page<Brand> page = brandRepo.findPaging(filter, pageable);
		List<BrandDto> content = BrandProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

}
