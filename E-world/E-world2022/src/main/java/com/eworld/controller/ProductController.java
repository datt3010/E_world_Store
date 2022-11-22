package com.eworld.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Category;
import com.eworld.repository.CategoryRepository;

@Controller
@RequestMapping("admin")
public class ProductController {
		
	@Autowired
	private CategoryRepository cateRepo;
	
	@RequestMapping("/listproduct")
	public String listProduct() {
		return "admin/product/ListProduct";
	}
	
	@RequestMapping("/product")
	public String crudProduct() {
		return "admin/product/ProductDashBoard";
	}
	
	@ModelAttribute("listCate")
	public List<Category> getCategory() {
		return  cateRepo.findByInStatus(CategoryStatus.ACTIVE);
	}
}
