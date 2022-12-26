package com.eworld.controller.user;

import com.eworld.dto.product.ProductDto;
import com.eworld.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class RestShoppingCartController {
    @Autowired
    ProductService productService;

    @GetMapping("{id}")
    public ProductDto getOne(@PathVariable("id")Integer id){
        return productService.findbyId(id);
    }
}
