package com.eworld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Category;
import com.eworld.repository.CategoryRepository;
import com.eworld.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public List<Category> findAll(CategoryStatus status) {
		return categoryRepo.findByInStatus(status);
	}

	@Override
	public void create(Category category) {
		categoryRepo.save(category);
	}

}
