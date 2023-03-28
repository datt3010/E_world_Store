package com.eworld.service.impl;

import com.eworld.configuration.security.UserContext;
import com.eworld.contstant.OrderStatus;
import com.eworld.contstant.UserStatus;
import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.order.OrderDto;
import com.eworld.entity.Order;
import com.eworld.filter.OrderFilter;
import com.eworld.projector.CustomerProjector;
import com.eworld.projector.OrderProjector;
import com.eworld.repository.customer.CustomerRepository;
import com.eworld.repository.order.OrderDetailRepository;
import com.eworld.repository.order.OrderRepository;
import com.eworld.repository.role.AccountRoleRepository;
import com.eworld.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
		
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private AccountRoleRepository accountRoleRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Page<OrderDto> findpaging(OrderFilter filter, Pageable pageable ) {
		Page<Order> page = orderRepository.findPaging(filter, pageable);
		List<OrderDto> content = OrderProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	public OrderDto findById(Integer id) {
		Order order =orderRepository.findById(id).orElseThrow();
		return  OrderDto.builder()
				.id(order.getId())
				.phone(order.getPhone())
				.address(order.getAddress())
				.createdAt(order.getCreatedAt())
				.paymentMethod(order.getPaymentMethod())
				.totalPrice(order.getTotalPrice())
				.orderDetails(order.getOrderDetails())
				.account(UserContext.builder()
						.username(order.getAccount().getUsername()).build())
				.build();
	}

	@Override
	public List<OrderDto> findByUserName(String username) {
		List<OrderDto> list = OrderProjector.convertToPageDto(orderRepository.findByUserName(username));
		return list;
	}

	@Override
	public List<CustomerDto> listAccount() {
		List<CustomerDto> list = CustomerProjector.convertToPageDto(customerRepository.listAccountByStatus(UserStatus.ACTIVE));
		return list;
	}

	@Override
	@Transactional
	public OrderDto changeStatus(OrderStatus status, Integer id) {
		Order order = orderRepository.findById(id).orElseThrow();
		order.setStatus(status);
		orderRepository.save(order);
		OrderDto dto = OrderProjector.convertToPageDto(order);
		return dto;
	}

	@Override
	public Long sumRevenueByMonth(Integer month) {
		return orderRepository.sumRevenueByMonth(month);
	}

	@Override
	public Long sumRevenueByYear(Integer years) {
		return orderRepository.sumRevenueByYear(years);
	}


}
