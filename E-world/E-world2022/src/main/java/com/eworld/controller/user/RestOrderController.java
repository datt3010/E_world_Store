package com.eworld.controller.user;

import com.eworld.dto.order.OrderDto;
import com.eworld.repository.order.OrderRepository;
import com.eworld.service.ShoppingCartService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/orders")
public class RestOrderController {

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping()
    public OrderDto create(@RequestBody JsonNode orderData){
        return shoppingCartService.createOrder(orderData);
    }
}
