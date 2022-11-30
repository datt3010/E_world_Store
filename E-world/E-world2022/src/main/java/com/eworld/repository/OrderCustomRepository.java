package com.eworld.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.entity.Order;
import com.eworld.filter.OrderFilter;

public interface OrderCustomRepository {
	
	Page<Order> findPaging(OrderFilter filter, Pageable pageable);

}
