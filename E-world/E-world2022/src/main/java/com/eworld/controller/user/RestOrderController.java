package com.eworld.controller.user;

import com.eworld.dto.order.OrderDetailDto;
import com.eworld.dto.order.OrderDto;
import com.eworld.repository.order.OrderRepository;
import com.eworld.service.OrderDetailService;
import com.eworld.service.ShoppingCartService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/orders")
public class RestOrderController {

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping()
    public OrderDto create(@RequestBody JsonNode orderData){
        return shoppingCartService.createOrder(orderData);
    }

    @GetMapping("/orderDetail/{id}")
    public List<OrderDetailDto> findByOrderId(@PathVariable("id")Integer id){
        List<OrderDetailDto> dto = orderDetailService.findByOrderId(id);
        return dto;
    }
}
