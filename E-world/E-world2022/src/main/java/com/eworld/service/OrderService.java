package com.eworld.service;

import com.eworld.contstant.OrderStatus;
import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.order.OrderDto;
import com.eworld.filter.OrderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
	 Page<OrderDto> findpaging(OrderFilter filter, Pageable pageable);
	 OrderDto findById(Integer id);
	 List<OrderDto> findByUserName(String username);

	 List<CustomerDto> listAccount();

	 OrderDto changeStatus(OrderStatus status, Integer id);

	 Long sumRevenueByMonth(Integer month);

	 Long sumRevenueByYear(Integer years);
}
