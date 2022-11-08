package com.eworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eworld.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
