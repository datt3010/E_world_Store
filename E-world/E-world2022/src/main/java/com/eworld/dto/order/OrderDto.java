package com.eworld.dto.order;

import java.util.Date;
import java.util.Set;

import com.eworld.configuration.security.UserContext;
import com.eworld.contstant.OrderStatus;
import com.eworld.contstant.PaymentMethod;
import com.eworld.dto.customer.CustomerDto;
import com.eworld.entity.Account;
import com.eworld.entity.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

	private Date createdAt;

	private Integer id;
	
	private Integer accountId;

	private String phone;
	
	private String address;
	
	private Double totalPrice;

	private OrderStatus status;
	
	private Account account;
	
	private PaymentMethod paymentMethod;
	
	private Set<OrderDetail> orderDetails;
}
