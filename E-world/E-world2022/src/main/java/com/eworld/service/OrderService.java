package com.eworld.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.dto.order.OrderDto;
import com.eworld.dto.order.OrderInput;
import com.eworld.filter.OrderFilter;

public interface OrderService {
	
	public OrderDto Checkout(OrderInput input);
	
	public Page<OrderDto> findpaging(OrderFilter filter, Pageable pageable);
}
