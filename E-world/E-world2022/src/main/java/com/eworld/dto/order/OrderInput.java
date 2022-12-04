package com.eworld.dto.order;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.eworld.contstant.OrderStatus;
import com.eworld.contstant.PaymentMethod;
import com.eworld.entity.OrderDetail;
import com.eworld.entity.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderInput {
	
	private Integer id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created_at;
	
	private String address; 
	
	private String phone ;
	
	private Double totalPrice;
	
	private Integer accountId;
	
	private PaymentMethod paymentMethod;
	
	private OrderStatus status;
	
	private Set<OrderDetail> orderDetails;
	
	private Product product;
}
