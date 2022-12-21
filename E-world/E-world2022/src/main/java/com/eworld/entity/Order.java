package com.eworld.entity;

import com.eworld.contstant.OrderStatus;
import com.eworld.contstant.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
		
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderDetail> orderDetails;
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name ="accountId", referencedColumnName = "id", updatable = false)
	private Account account;
	
}

