package com.eworld.controller;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eworld.entity.Category;
import com.eworld.service.CategoryService;
import com.eworld.service.UploadService;

@Controller
@RequestMapping("/admin")
public class CategoryController {  
	
	@Autowired
	private CategoryService categorySerivce;
	
	@Autowired
	private UploadService uploadService;
	
	ServletContext application;
	
	@PostMapping("/category/insert")
	public String create (@ModelAttribute("category") Category category, Model model, @RequestParam("image") MultipartFile file) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		category.setLogo(fileName);
		
		categorySerivce.create(category);
		String uploadDirectory = "src/main/resources/static/images/product/" +category.getId();
		uploadService.save(file, uploadDirectory);
		model.addAttribute("message","Thêm mới thành công");
		
		return "forward:/admin/brand";
	}
	
	@RequestMapping("/brand")
	public String crudBrand(Model model) {
		model.addAttribute("category", new Category());
		return "admin/brand/BrandDashBoard";
	}
	
	@RequestMapping("/listcategory")
	public String listBrand() {
		return "admin/brand/ListBrand";
	}
	
}
