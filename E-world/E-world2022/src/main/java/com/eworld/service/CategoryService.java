package com.eworld.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Category;

public interface CategoryService {
	
	public List<Category> findAll(CategoryStatus status);
	
	public void create(Category category);
	
	public void update(Category category);
	
	public Page<Category> findByKeyWord(String keyword, Pageable pageable);
}
