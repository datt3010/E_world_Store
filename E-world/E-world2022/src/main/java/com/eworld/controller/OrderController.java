package com.eworld.controller;

import com.eworld.dto.order.OrderDto;
import com.eworld.filter.OrderFilter;
import com.eworld.service.OrderService;
import com.eworld.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ShoppingCartService cartService;

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
