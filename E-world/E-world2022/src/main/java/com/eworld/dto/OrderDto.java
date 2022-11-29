package com.eworld.dto;

import java.util.Date;

import com.eworld.contstant.OrderStatus;
import com.eworld.entity.Account;
import com.eworld.entity.PaymentMethod;

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
	
	private Integer paymentId;
	
	private String phone;
	
	private String address;
	
	private Double totalPrice;
	
	private Double freightFee;
	
	private OrderStatus status;
	
	private Account account;
	
	private PaymentMethod paymentMethod;
}
