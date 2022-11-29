package com.eworld.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {
	
	@CreatedDate
	@Column(updatable = false)
	private Date createdAt;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String phone;
	
	private String address;
	
	private Double totalPrice;
	
	private Double freightFee;
	
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderDetail> orderDetails;
	
	@Column(updatable = false , insertable = false)
	private Integer accountId;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name ="accountId", referencedColumnName = "id", updatable = false)
	private Account account;
	
	@Column(updatable = false , insertable = false)
	private Integer paymentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paymentId", referencedColumnName = "id", updatable = false)
	private PaymentMethod paymentMethod;
}

