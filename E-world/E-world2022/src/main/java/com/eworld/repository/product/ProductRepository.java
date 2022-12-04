package com.eworld.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eworld.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductCustomRepository {

}
