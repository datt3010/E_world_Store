package com.eworld.controller;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eworld.contstant.CategoryStatus;
import com.eworld.dto.product.ProductDto;
import com.eworld.dto.product.ProductInput;
import com.eworld.dto.product.ProductUpdate;
import com.eworld.entity.Category;
import com.eworld.filter.ProductFilter;
import com.eworld.repository.category.CategoryRepository;
import com.eworld.service.ProductService;
import com.eworld.service.UploadService;

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
	public String listProduct(Model model, @RequestParam(name = "keyword", required = false)String keyword) {
		Pageable pageable = PageRequest.of(0, 10,Direction.ASC,"name");
		
		ProductFilter filter = ProductFilter.builder()
				.keyword(keyword)
				.build();
		Page<ProductDto> listProduct = productService.findPaging(filter, pageable);
		model.addAttribute("listProduct", listProduct);
		
		return listPage(model, 1, keyword,"name", "asc", pageable);
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
			@RequestParam("image") MultipartFile file) throws IOException {
			
		if(result.hasErrors()) {
			return"admin/product/ProductDashBoard";
		}
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		input.setLogo(fileName);
		productService.create(input);
		String uploadDirectory = "src/main/resources/static/images/product/";
		uploadService.save(file, uploadDirectory);
		model.addAttribute("message", "Insert thành công");
		
		return "forward:/admin/product";
	}
	
	@RequestMapping("/listproduct/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		productService.deleteById(id);
		return "redirect:/admin/listproduct";
	}
	
	@RequestMapping("/listproduct/{id}")
	public String detail(Model model,@PathVariable("id") Integer id) {
		ProductDto dto = productService.getDetail(id);
		model.addAttribute("product",dto);	
		return "admin/product/ProductDashBoard";
	}
	
	@PostMapping("listproduct/{id}")
	public String update(Model model,
			@PathVariable("id") Integer id, 
			@ModelAttribute("product") @Valid ProductUpdate input,
			BindingResult result,
			@RequestParam("image") MultipartFile file) throws IOException,MissingPathVariableException {
			
			if(result.hasErrors()) {
				return "admin/product/ProductDashBoard";
			}
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			input.setLogo(fileName);
			
			productService.update(id, input);
			String uploadDir = "src/main/resources/static/images/staff/";
			uploadService.save(file, uploadDir);
			model.addAttribute("message", "Update thành công");
		
			return "admin/product/ProductDashBoard";
	}
	
	@RequestMapping("listproduct/page/{pageNum}")
	public String listPage(Model model, 
			@PathVariable("pageNum") int pageNum,
			@RequestParam(name="keyword", required = false)String keyword,
			@RequestParam(name="sortField", required = false) String sortField,
			@RequestParam(name="sortDir", required = false) String sortDir,
			@SortDefault(sort = "name", direction = Direction.ASC)Pageable pageable) {
		
		 pageable = PageRequest.of(pageNum-1, 3,sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
		ProductFilter filter = ProductFilter.builder()
				.keyword(keyword)
				.build();
		Page<ProductDto> listProduct = productService.findPaging(filter, pageable);
		model.addAttribute("keyword", filter.getKeyword());
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", listProduct.getTotalPages());
		model.addAttribute("totalItems", listProduct.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");
		return "admin/product/ListProduct";
		}
}
