package com.eworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eworld.dto.ProductDto;
import com.eworld.filter.ProductFilter;
import com.eworld.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping()
	public String home(Model model,@RequestParam(name="keyword", required = false)String keyword) {
		Pageable pageable = PageRequest.of(0,4,Direction.ASC,"name");
		
		ProductFilter filter = ProductFilter.builder()
				.keyword(keyword)
				.build();
		Page<ProductDto> listProduct = productService.findPaging(filter, pageable);
		model.addAttribute("listProduct", listProduct);
		
		return "user/home/Index";
	}
	
	@GetMapping("about-us")
	public String aboutUs() {
		return "user/home/about-us";
	}
	
	@GetMapping("contact")
	public String contact() {
		return "user/home/contact";
	}
	
	@GetMapping("dangnhap")
	public String login() {
		return "/user/login/login";
	}

	@GetMapping("chitietsanpham")
	public String detailProduct() {
		return "user/product/ProductDetail";
	}

	@GetMapping("logout")
	public String logOut() {
		return "user/login/login";
	}
	
	@GetMapping("404")
	public String error() {
		return "user/error/404";
	}
	
	@RequestMapping("dangky")
	public String dangky() {
		return "user/login/register";
	}
	
	@GetMapping("blogdetail")
	public String blogDetail() {
		return "user/blog/BlogDetail";
	}
	
	@GetMapping("listblog")
	public String listBlog() {
		return "user/blog/blog-list";
	}
}
