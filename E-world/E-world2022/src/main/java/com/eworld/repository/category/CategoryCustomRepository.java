package com.eworld.repository.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.entity.Category;
import com.eworld.filter.CategoryFilter;

public interface CategoryCustomRepository{
	
	Page<Category> findPaging(CategoryFilter filter, Pageable pageable);

}
