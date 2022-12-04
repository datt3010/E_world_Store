package com.eworld.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.contstant.CategoryStatus;
import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.category.CategoryInput;
import com.eworld.dto.category.CategoryUpdate;
import com.eworld.entity.Category;
import com.eworld.filter.CategoryFilter;

public interface CategoryService {
	
	public List<Category> findAll(CategoryStatus status);
	
	public  CategoryDto create(CategoryInput input);
	
	public 	CategoryDto update(Integer id, CategoryUpdate input);
	
	public Page<CategoryDto> findPaging(CategoryFilter filter, Pageable pageable);
		
	public void deleteById(Integer id);
	
	public CategoryDto getDetail(Integer id);
		
}
