package com.eworld.entity;

import com.eworld.contstant.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {
	
	@CreatedDate
	@Column(updatable = false)
	private Date createdAt;

	private Date updatedAt;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(insertable = false, updatable = false)
	private Integer categoryId;
	
	private String name;
	
	private Double price;
	
	private Integer quantity;
		
	private String urlVideo;
	
	private Date ngaybaohanh;

	private Integer views;
	
	private String description;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId", referencedColumnName = "id")
	@JsonIgnore
	private Category category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<OrderDetail> orderDetails;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<ProductImages> productImages;


}
