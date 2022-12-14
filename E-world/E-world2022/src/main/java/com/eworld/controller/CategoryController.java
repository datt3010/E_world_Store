package com.eworld.controller;

import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.category.CategoryInput;
import com.eworld.dto.category.CategoryUpdate;
import com.eworld.entity.Category;
import com.eworld.service.CategoryService;
import com.eworld.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class CategoryController {  
	
	@Autowired
	private CategoryService categorySerivce;
	
	@Autowired
	private UploadService uploadService;

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
		
		return "admin/category/CategoryDashBoard";
	}
	
	@RequestMapping("/category")
	public String crudCategory(Model model) {
		
		model.addAttribute("category", new Category());
		
		return "admin/category/CategoryDashBoard";
	}
	
	@RequestMapping("/listcategory")
	public String listCategory(Model model) {
		return listPage(model, 1, null, "id", "asc");
	}
	
	@RequestMapping("category/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		CategoryDto category = categorySerivce.getDetail(id);
		
		model.addAttribute("category",category);
		return "admin/category/CategoryDashBoard";
	}
	
	@RequestMapping("category/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		categorySerivce.changeStatus(id);
		return "redirect:/admin/listcategory";
	}
	
	@PostMapping("/category/{id}")
	public String update(@ModelAttribute("category") @Valid  CategoryUpdate input, BindingResult result, Model model, @RequestParam("image") MultipartFile file, @PathVariable("id") Integer id) throws IOException {
		
		if(result.hasErrors()) {
			return "admin/category/CategoryDashBoard";
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		input.setLogo(fileName);
		
		categorySerivce.update(id,input);
		String uploadDirectory = "src/main/resources/static/images/product/";
		uploadService.save(file, uploadDirectory);
		model.addAttribute("message","Update thành công ^-^");
		
		return "forward:/admin/category";
	}
	
	@RequestMapping("listcategory/page/{pageNum}")
	public String listPage(Model model,
			@PathVariable("pageNum") int pageNum,
			@Param("keyword") String keyword,
			@Param("sortField") String sortField,
		   	@Param("sortDir") String sortDir)
	{
		Page<CategoryDto> listCategory = categorySerivce.findPaging(keyword, sortField, sortDir, pageNum);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", listCategory.getTotalPages());
		model.addAttribute("totalItems", listCategory.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listCategory", listCategory);

		return "admin/category/ListCategory";
	}

}
