package com.eworld.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eworld.dto.CategoryDto;
import com.eworld.dto.CategoryInput;
import com.eworld.dto.CategoryUpdate;
import com.eworld.entity.Category;
import com.eworld.filter.CategoryFilter;
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
	public String create (@ModelAttribute("category") @Valid CategoryInput input, 
			BindingResult result,
			Model model, 
			@RequestParam("image") MultipartFile file) throws IOException {
		
		if(result.hasErrors()) {
			return "admin/brand/BrandDashBoard";
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		input.setLogo(fileName);
		
		categorySerivce.create(input);
		String uploadDirectory = "src/main/resources/static/images/product/" +input.getId();
		uploadService.save(file, uploadDirectory);
		model.addAttribute("message","Thêm mới thành công ^-^");
		
		return "admin/brand/BrandDashBoard";
	}
	
	@RequestMapping("/category")
	public String crudBrand(Model model) {
		
		model.addAttribute("category", new Category());
		
		return "admin/brand/BrandDashBoard";
	}
	
	@RequestMapping("/listcategory")
	public String listBrand(Model model, @RequestParam(name = "keyword", required = false)String keyword) {
		Pageable pageable = PageRequest.of(0, 10, Direction.ASC,"name");
		
		CategoryFilter filter = CategoryFilter.builder()
				.keyword(keyword)
				.build();
		
		Page<CategoryDto> listCategory = categorySerivce.findPaging(filter, pageable);
		model.addAttribute("listCategory", listCategory);
		return "admin/brand/ListBrand";
	}
	
	@RequestMapping("category/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		CategoryDto category = categorySerivce.getDetail(id);
		
		model.addAttribute("category",category);
		return "admin/brand/BrandDashBoard";
	}
	
	@RequestMapping("category/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		categorySerivce.deleteById(id);
		return "redirect:/admin/listcategory";
	}
	
	@PostMapping("/category/{id}")
	public String update(@ModelAttribute("category") @Valid  CategoryUpdate input, BindingResult result, Model model, @RequestParam("image") MultipartFile file, @PathVariable("id") Integer id) throws IOException {
		
		if(result.hasErrors()) {
			return "admin/brand/BrandDashBoard";
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		input.setLogo(fileName);
		
		categorySerivce.update(id,input);
		String uploadDirectory = "src/main/resources/static/images/product/";
		uploadService.save(file, uploadDirectory);
		model.addAttribute("message","Update thành công ^-^");
		
		return "forward:/admin/category";
	}
}
