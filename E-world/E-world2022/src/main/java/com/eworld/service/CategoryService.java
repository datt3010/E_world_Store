package com.eworld.service;

import java.util.List;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Category;

public interface CategoryService {
	
	public List<Category> findAll(CategoryStatus status);
}