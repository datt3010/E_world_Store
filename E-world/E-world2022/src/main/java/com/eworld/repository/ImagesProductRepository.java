package com.eworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eworld.entity.ImagesProduct;

public interface ImagesProductRepository extends JpaRepository<ImagesProduct, Integer> {
	
	List<ImagesProduct> findByProductId( Integer productId);
	
}