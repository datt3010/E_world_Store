package com.eworld.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eworld.dto.ProductDto;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;
import com.eworld.repository.ProductRepository;
import com.eworld.service.ProductService;
import com.eworld.service.ShoppingCartService;

@Controller
public class WebController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ProductRepository producRepo;
	
	@RequestMapping("chitietsanpham/{id}")
	public String getDetails(Model model, @PathVariable("id")Integer id) {
		
		ProductDto dto = productService.getDetail(id);
		
		model.addAttribute("product", dto);
		
		return "user/product/ProductDetail";
	}
	
	@RequestMapping("/product")
	public String listAll(Model model, @RequestParam(name = "keyword", required = false)String keyword) {
	Pageable pageable = PageRequest.of(0,5,Direction.ASC,"name");		
		return listPage(model, 1, keyword,"name", "asc", pageable);
	}
	
	@RequestMapping("/product/page/{pageNum}")
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
		return "user/product/Listproduct";
		}
	
	@RequestMapping("/product/search")
	public String searchByKey(Model model,
					@RequestParam("keyword")String keyword,
					@SortDefault(sort = "name", direction = Direction.DESC)Pageable pageable) {
		pageable = PageRequest.of(0, 10,Direction.ASC,"name");
		
		ProductFilter filter = ProductFilter.builder()
				.keyword(keyword)
				.build();
		Page<ProductDto> listProduct = productService.findPaging(filter, pageable);
		model.addAttribute("listProduct", listProduct);
		
		return listPage(model, 1, keyword,"name", "asc", pageable);
	}
	
	@RequestMapping("/shoppingcart")
	public String shoppingCart(Model model) {
		model.addAttribute("cart", shoppingCartService.getProductInCart());
		return "user/product/Cart";
	}
	@RequestMapping("/shoppingcart/addProduct/{productId}")
	public String addProductToCart(@PathVariable("productId") Integer productId) {
		producRepo.findById(productId).ifPresent(shoppingCartService :: addProduct);
		return "user/product/Cart";
	}
	
	@RequestMapping("/shoppingcart/removeProduct/{productId}")
	public String removeProduct(@PathVariable("productId") Integer productId) {
		producRepo.findById(productId).ifPresent(shoppingCartService :: removeProduct);
		return "user/product/Cart";
	}
	
	@ModelAttribute("cart")
	public Map<Product, Integer> listCart(Model model){
		model.addAttribute("totalItems", shoppingCartService.getProductInCart().values());
		return shoppingCartService.getProductInCart();
	}
}
