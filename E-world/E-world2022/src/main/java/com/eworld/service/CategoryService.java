package com.eworld.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Category;
import com.eworld.schema.category.CategoryDto;
import com.eworld.schema.category.CategoryInput;
import com.eworld.schema.category.CategoryUpdate;

public interface CategoryService {
	
	public List<Category> findAll(CategoryStatus status);
	
	public  CategoryDto create(CategoryInput input);
	
	public 	CategoryDto update(CategoryUpdate input);
	
	public Page<Category> findByKeyWord(String keyword, Pageable pageable);
		
	public void deleteById(Integer id);
}
