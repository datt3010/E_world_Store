package com.eworld.service;

import com.eworld.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.dto.order.OrderDto;
import com.eworld.dto.order.OrderInput;
import com.eworld.filter.OrderFilter;

import java.util.List;
import java.util.Map;

public interface OrderService {
	 Page<OrderDto> findpaging(OrderFilter filter, Pageable pageable);
	 OrderDto findById(Integer id);
	 List<OrderDto> findByUserName(String username);

}
