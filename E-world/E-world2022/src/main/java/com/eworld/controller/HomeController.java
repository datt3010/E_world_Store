package com.eworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@GetMapping()
	public String home() {
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
	
	@GetMapping("sanpham")
	public String product() {
		return "user/product/Listproduct";
	}
	
	@GetMapping("chitietsanpham")
	public String detailProduct() {
		return "user/product/ProductDetail";
	}
	
	@GetMapping("giohang")
	public String wishList() {
		return "user/product/wishlist";
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
	//-------------Request admin--------------//
	@RequestMapping("/admin")
	public String homeAdmin() {
		return "admin/index";
	}
	@RequestMapping("/admin/listproduct")
	public String listProduct() {
		return "admin/product/ListProduct";
	}
	
	@RequestMapping("/admin/product")
	public String crudProduct() {
		return "admin/product/ProductDashBoard";
	}
	
	@RequestMapping("/admin/customer")
	public String crudCustomer() {
		return "admin/customer/CustomerDashBoard";
	}
	
	@RequestMapping("/admin/listcustomer")
	public String listCustomer() {
		return "admin/customer/ListCustomer";
	}
	
	@RequestMapping("/admin/staff")
	public String crudStaff() {
		return "admin/staff/StaffDashBoard";
	}
	
	@RequestMapping("/admin/liststaff")
	public String listStaff() {
		return "admin/staff/ListStaff";
	}
	
	@RequestMapping("/admin/brand")
	public String crudBrand() {
		return "admin/brand/BrandDashBoard";
	}
	
	@RequestMapping("/admin/listbrand")
	public String listBrand() {
		return "admin/brand/ListBrand";
	}
	
	@RequestMapping("/admin/login")
	public String adminLogin() {
		return "admin/login";
	}
	
	@RequestMapping("/admin/blog")
	public String adminBlog() {
		return "admin/blog/BlogDashBoard";
	}
	
	@RequestMapping("/admin/listblog")
	public String listAdminBlog() {
		return "admin/blog/ListBlog";
	}
}
