package com.eworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping()
	public String home() {
		return "user/home/Index.html";
	}
	
	@GetMapping("about-us")
	public String aboutUs() {
		return "user/home/about-us.html";
	}
	
	@GetMapping("contact")
	public String contact() {
		return "user/home/contact.html";
	}
	
	@GetMapping("dangnhap")
	public String login() {
		return "user/login/login.html";
	}
	
	@GetMapping("sanpham")
	public String product() {
		return "user/product/Listproduct.html";
	}
	
	@GetMapping("chitietsanpham")
	public String detailProduct() {
		return "user/product/ProductDetail.html";
	}
	
	@GetMapping("giohang")
	public String Wishlist() {
		return "user/product/wishlist.html";
	}
}
