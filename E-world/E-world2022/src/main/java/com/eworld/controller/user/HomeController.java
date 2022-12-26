package com.eworld.controller.user;

import com.eworld.entity.Product;
import com.eworld.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public String home(Model model){
        return "user/home/Index";
    }

    @GetMapping("about-us")
    public String aboutUs() {
        return "user/home/about-us";
    }

/*    @GetMapping("blogdetail")
    public String blogDetail() {
        return "user/blog/BlogDetail";
    }

    @GetMapping("listblog")
    public String listBlog() {
        return "user/blog/blog-list";
    }*/

    @ModelAttribute("smartPhones")
   public List<Product> listProductByCategoryId(){
        Pageable pageable = PageRequest.of(0, 3, Sort.by("name").descending());
        Page<Product> page = productService.findProductByCategoryId(1,pageable);
        return  page.getContent();
    }

    @ModelAttribute("Mobiles")
    public List<Product> listProductByMobiles(){
        Pageable pageable = PageRequest.of(0,3,Sort.by("name").descending());
        Page<Product> page = productService.findProductByCategoryId(2,pageable);
        return page.getContent();
    }
    @ModelAttribute("laptops")
    public List<Product> listProductByLaptops(){
        Pageable pageable = PageRequest.of(0,3,Sort.by("name").descending());
        Page<Product> page = productService.findProductByCategoryId(3,pageable);
        return page.getContent();
    }

}
