package com.eworld.service;

import com.eworld.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.dto.order.OrderDto;
import com.eworld.dto.order.OrderInput;
import com.eworld.filter.OrderFilter;

import java.util.Map;

public interface OrderService {
	
	public OrderDto Checkout(OrderInput input);
	
	public Page<OrderDto> findpaging(OrderFilter filter, Pageable pageable);
}
