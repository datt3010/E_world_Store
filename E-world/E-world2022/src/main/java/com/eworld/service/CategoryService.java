package com.eworld.service;

import com.eworld.contstant.CategoryStatus;
import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.category.CategoryInput;
import com.eworld.dto.category.CategoryUpdate;
import com.eworld.entity.Category;
import com.eworld.filter.CategoryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
	
	public List<Category> findAll(CategoryStatus status);
	
	public  CategoryDto create(CategoryInput input);
	
	public 	CategoryDto update(Integer id, CategoryUpdate input);
	
	public Page<CategoryDto> findPaging(CategoryFilter filter, Pageable pageable);
		
	public void changeStatus(Integer id);
	
	public CategoryDto getDetail(Integer id);

	public List<CategoryDto> listCategoryHotSale(Integer month);
		
}
