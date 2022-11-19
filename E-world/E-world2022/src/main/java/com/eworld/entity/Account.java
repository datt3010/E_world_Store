package com.eworld.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Data
public class Account  {
	
	@CreatedDate 
	@Column(updatable = false)
	private Date createdAt;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private Integer age;
	
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Gender gioitinh;
	
	private String firstName;
	
	private String lastName;
	
	private Date dateOfBirth;
	
	private String address;
	
	private String nationality;
	
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	 Set<Order> orders;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	 Set<UserRole> userRoles;
	
	public Account(OAuth2User socialUser) {
		this.username = socialUser.getName();
	}
}
