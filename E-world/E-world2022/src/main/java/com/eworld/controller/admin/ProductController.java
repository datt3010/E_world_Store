package com.eworld.controller.admin;

import com.eworld.contstant.CategoryStatus;
import com.eworld.dto.product.ProductDto;
import com.eworld.dto.product.ProductInput;
import com.eworld.dto.product.ProductUpdate;
import com.eworld.entity.Category;
import com.eworld.filter.ProductFilter;
import com.eworld.repository.category.CategoryRepository;
import com.eworld.service.ProductService;
import com.eworld.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("admin")
public class ProductController {
		
	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping("/listproduct")
	public String listProduct(Model model) {
		return listPage(model,1,null,"id","asc",null);
	}
	
	@RequestMapping("/product")
	public String crudProduct(Model model) {
		model.addAttribute("product",new ProductDto());
		return "admin/product/ProductDashBoard";
	}
	
	@ModelAttribute("listCate")
	public List<Category> getCategory() {
		return  cateRepo.findByInStatus(CategoryStatus.ACTIVE);
	}
	
	@PostMapping("/product/insert")
	public String insert(Model model,@ModelAttribute("product") @Valid ProductInput input,
			BindingResult result,
			@RequestParam("image") MultipartFile[] file) throws IOException {

		if(result.hasErrors()) {
			return"admin/product/ProductDashBoard";
		}
		String uploadDirectory = "src/main/resources/static/images/product/";
		List<String> fileNames = uploadService.saveMulitpleFiles(Arrays.asList(file), uploadDirectory);
		productService.create(input,fileNames);
		model.addAttribute("message", "Insert thành công");
		
		return "forward:/admin/product";
	}
	
	@RequestMapping("/listproduct/delete/{id}")
	public String changeStatusToInactive(@PathVariable("id") Integer id) {
		productService.changeStatusToInactive(id);
		return "redirect:/admin/listproduct";
	}
	
	@RequestMapping("/listproduct/{id}")
	public String detail(Model model,@PathVariable("id") Integer id) {
		ProductDto dto = productService.getDetailById(id);
		model.addAttribute("product",dto);	
		return "admin/product/ProductDashBoard";
	}
	
	@PostMapping("listproduct/{id}")
	public String update(Model model,
			@PathVariable("id") Integer id, 
			@ModelAttribute("product") @Valid ProductUpdate input,
			BindingResult result,
			@RequestParam("image") MultipartFile[] file) throws IOException,MissingPathVariableException {
			
			if(result.hasErrors()) {
				return "admin/product/ProductDashBoard";
			}
		String uploadDirectory = "src/main/resources/static/images/product/";
		List<String> fileNames = uploadService.saveMulitpleFiles(Arrays.asList(file), uploadDirectory);
		productService.update(id, input, fileNames);
			model.addAttribute("message", "Update thành công");
		
			return "admin/product/ProductDashBoard";
	}
	
	@RequestMapping("listproduct/page/{pageNum}")
	public String listPage(Model model, 
			@PathVariable("pageNum") int pageNum,
			@RequestParam(name="keyword", required = false)String keyword,
			@RequestParam(name="sortField", required = false) String sortField,
			@RequestParam(name="sortDir", required = false) String sortDir,
		   @RequestParam(value = "categoryId", required = false) Integer categoryId
		) {
		Pageable pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		ProductFilter filter = ProductFilter.builder()
				.keyword(keyword)
				.categoryId(categoryId)
				.build();
		Page<ProductDto> listProduct = productService.findPaging(filter,pageable);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", listProduct.getTotalPages());
		model.addAttribute("totalItems", listProduct.getTotalElements());
		
		model.addAttribute("sortField", "id");
		model.addAttribute("sortDir", "asc");
		model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

		model.addAttribute("listProduct", listProduct);

		return "admin/product/ListProduct";
		}
}
