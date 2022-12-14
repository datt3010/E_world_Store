package com.eworld.controller;

import com.eworld.dto.product.ProductDto;
import com.eworld.filter.ProductFilter;
import com.eworld.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public String home(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
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

    @GetMapping("blogdetail")
    public String blogDetail() {
        return "user/blog/BlogDetail";
    }

    @GetMapping("listblog")
    public String listBlog() {
        return "user/blog/blog-list";
    }
}
