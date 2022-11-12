package com.eworld.controller;

import java.io.IOException;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eworld.entity.Category;
import com.eworld.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {  
	
	@Autowired
	private CategoryService categorySerivce;
	
	@PostMapping("/category/insert")
	public String create (@ModelAttribute("category") Category category, Model model, @RequestParam("logo") MultipartFile file) throws IOException {
		
		categorySerivce.create(category);
				
		return "redirect:/admin/brand";
	}
	
	@RequestMapping("/brand")
	public String crudBrand(Model model) {
		model.addAttribute("category", new Category());
		return "admin/brand/BrandDashBoard";
	}
	
	@RequestMapping("/listbrand")
	public String listBrand() {
		return "admin/brand/ListBrand";
	}
	
}
