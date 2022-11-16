package com.eworld.controller;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eworld.entity.Category;
import com.eworld.repository.CategoryRepository;
import com.eworld.schema.category.CategoryInput;
import com.eworld.schema.category.CategoryUpdate;
import com.eworld.service.CategoryService;
import com.eworld.service.UploadService;

@Controller
@RequestMapping("/admin")
public class CategoryController {  
	
	@Autowired
	private CategoryService categorySerivce;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private CategoryRepository cateRepo;
	
	ServletContext application;
	
	@PostMapping("/category/insert")
	public String create (@ModelAttribute("category") CategoryInput input, Model model, @RequestParam("image") MultipartFile file) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		input.setLogo(fileName);
		
		categorySerivce.create(input);
		String uploadDirectory = "src/main/resources/static/images/product/" +input.getId();
		uploadService.save(file, uploadDirectory);
		model.addAttribute("message","Thêm mới thành công ^-^");
		
		return "forward:/admin/category";
	}
	
	@RequestMapping("/category")
	public String crudBrand(Model model) {
		
		model.addAttribute("category", new Category());
		
		return "admin/brand/BrandDashBoard";
	}
	
	@RequestMapping("/listcategory/search")
	public String listBrand(Model model, @RequestParam("keyword") String keyword) {
		
		Pageable page = PageRequest.of(0,10, Direction.ASC,"name");
		Page<Category> listCate = categorySerivce.findByKeyWord(keyword, page);
		model.addAttribute("listCate", listCate);
		
		return "admin/brand/ListBrand";
	}
	
	@RequestMapping("/listcategory")
	public String listBrand(Model model) {
		Pageable page = PageRequest.of(0, 10, Direction.ASC,"name");
		Page<Category> listCate = cateRepo.findAll(page);
		model.addAttribute("listCate", listCate);
		return "admin/brand/ListBrand";
	}
	
	@RequestMapping("category/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
//		categorySerivce.getDetail(id);
		model.addAttribute("category",cateRepo.findById(id).get());
		return "admin/brand/BrandDashBoard";
	}
	
	@RequestMapping("category/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		categorySerivce.deleteById(id);
		return "redirect:/admin/listcategory";
	}
	
	@PostMapping("/category/{id}")
	public String update(@RequestBody CategoryUpdate input, Model model, @RequestParam("image") MultipartFile file, @PathVariable("id") Integer id) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		input.setLogo(fileName);
		
		categorySerivce.update(input);
		String uploadDirectory = "src/main/resources/static/images/product/";
		uploadService.save(file, uploadDirectory);
		model.addAttribute("message","Update thành công ^-^");
		
		return "forward:/admin/category";
	}
}
