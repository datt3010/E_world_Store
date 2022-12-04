package com.eworld.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eworld.contstant.OrderStatus;
import com.eworld.contstant.PaymentMethod;
import com.eworld.dto.order.OrderDto;
import com.eworld.dto.order.OrderInput;
import com.eworld.entity.Order;
import com.eworld.entity.OrderDetail;
import com.eworld.entity.Product;
import com.eworld.filter.OrderFilter;
import com.eworld.projector.OrderProjector;
import com.eworld.repository.order.OrderRepository;
import com.eworld.repository.product.ProductRepository;
import com.eworld.service.OrderService;
import com.eworld.service.ShoppingCartService;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
		
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ShoppingCartService cartService;
	
	@Autowired
	ProductRepository productRepo;
	
    private Map<Product, Integer> products = new HashMap<>();
	
	@Override
	@Transactional
	public OrderDto Checkout(OrderInput input) {
		
		Instant instant = Instant.now();
		Date date = Date.from(instant);
		
		 Product product = products.keySet().stream()
				.filter(pp -> pp.getId() == input.getProduct().getId())
				.findFirst()
				.orElse(input.getProduct());
		
		Order order = Order.builder()
				.createdAt(date)
				.id(input.getId())
				.address(input.getAddress())
				.phone(input.getPhone())
				.totalPrice(cartService.getTotal())
				.paymentMethod(PaymentMethod.CASH)
				.status(OrderStatus.IN_PROCRESS)
				.build();
				
		Set<OrderDetail> OrderDetails = productRepo.findById(product.getId()).stream()
				.map( e -> OrderDetail.builder()
						.order(order)
						.productPrice(product.getPrice())
						.quantity(product.getQuantity())
						.product(e)
						.build())
				.collect(Collectors.toSet());
		order.setOrderDetails(OrderDetails);
			
		orderRepository.save(order);
		return OrderDto.builder().id(input.getId()).build();
	}

	@Override
	public Page<OrderDto> findpaging(OrderFilter filter, Pageable pageable ) {
		Page<Order> page = orderRepository.findPaging(filter, pageable);
		List<OrderDto> content = OrderProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

}
