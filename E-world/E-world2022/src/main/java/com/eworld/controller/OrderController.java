package com.eworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eworld.dto.order.OrderDto;
import com.eworld.dto.order.OrderInput;
import com.eworld.filter.OrderFilter;
import com.eworld.service.OrderService;
import com.eworld.service.ShoppingCartService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ShoppingCartService cartService;
	
	@RequestMapping("/order")
	public String home(Model model) {
		
		model.addAttribute("order", new OrderInput());
		model.addAttribute("totalPrice", (cartService.getTotal()*0.1));
		return "user/order/Order";
	}
	 
	@PostMapping("/order")
	public String checkOut(Model model, @ModelAttribute("order") OrderInput input) {
				
		input.setAccountId(1);
		orderService.Checkout(input);		
		model.addAttribute("message", "order thanh cong");
		return "user/order/Order";
	}
	
	@ModelAttribute("listOrder")
	public Page<OrderDto> listOrder( @RequestParam(name = "keyword", required = false)String keyword) {
		
		Pageable pageable = PageRequest.of(0,3, Direction.ASC,"id");
		OrderFilter filter = OrderFilter.builder()
				.keyword(keyword)
				.build();
		Page<OrderDto> page = orderService.findpaging(filter, pageable);
		return page;
	}
}
