package com.eworld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eworld.contstant.CategoryStatus;
import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.category.CategoryInput;
import com.eworld.dto.category.CategoryUpdate;
import com.eworld.entity.Category;
import com.eworld.filter.CategoryFilter;
import com.eworld.projector.CategoryProjector;
import com.eworld.repository.category.CategoryRepository;
import com.eworld.service.CategoryService;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public List<Category> findAll(CategoryStatus status) {
		return categoryRepo.findByInStatus(status);
	}

	@Override
	@Transactional
	public CategoryDto create(CategoryInput input) {
		
		Category category = Category.builder()
				.name(input.getName())
				.logo(input.getLogo())
				.status(input.getStatus())
				.build();
		
		categoryRepo.save(category);
		
		return CategoryDto.builder()
				.id(category.getId())
				.build();
	}
	
	@Override
	@Transactional
	public CategoryDto update(Integer id , CategoryUpdate  input) {
		
		Category category = categoryRepo.findById(id).get();
		
		category.setName(input.getName());
		category.setLogo(input.getLogo());
		category.setStatus(input.getStatus());
		
		return CategoryDto.builder().id(category.getId()).build();
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		 categoryRepo.deleteById(id);
	}

	@Override
	public Page<CategoryDto> findPaging(CategoryFilter filter, Pageable pageable) {
		Page<Category> page = categoryRepo.findPaging(filter, pageable);
		List<CategoryDto> content = CategoryProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	public CategoryDto getDetail(Integer id) {
		
		Category category = categoryRepo.findById(id).get();
		CategoryDto dto = CategoryProjector.convertToDetailDto(category);
		
		return dto;
	}
}
