package com.eworld.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer quantity;
	
	private Double productPrice;

	@Column(insertable = false, updatable = false)
	private  Integer productId;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", referencedColumnName = "id", updatable = false)
	private Product product;

	@Column(insertable = false, updatable = false)
	private  Integer orderId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId", referencedColumnName = "id", updatable = false)
	private Order order;
}
