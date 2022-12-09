package com.eworld.repository.brand;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eworld.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>, BrandCustomRepository {
	
	
}