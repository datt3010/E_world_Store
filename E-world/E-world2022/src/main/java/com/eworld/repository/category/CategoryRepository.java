package com.eworld.repository.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryCustomRepository {
	
	@Query("FROM Category c WHERE c.status LIKE :status")
	List<Category> findByInStatus( @Param(value = "status") CategoryStatus status);
	
	@Query("FROM Category c WHERE c.name LIKE %:keyword% OR c.status LIKE %:keyword%")
	Page<Category> findByKeyWord(@Param(value = "keyword") String keyword, Pageable pageable);
	
}
