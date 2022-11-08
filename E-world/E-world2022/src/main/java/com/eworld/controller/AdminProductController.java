package com.eworld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eworld.entity.Product;
import com.eworld.repository.ProductRepository;

@CrossOrigin("*")
@RestController
public class AdminProductController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/admin/listproduct")
	public List<Product> getAll(){
		return productRepo.findAll();
	}
}
