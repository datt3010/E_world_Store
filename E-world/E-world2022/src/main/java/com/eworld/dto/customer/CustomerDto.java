package com.eworld.dto.customer;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.dto.profile.AccountProfileDto;
import com.eworld.entity.Order;
import com.eworld.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CustomerDto {

	private Date createdAt;
	
	private Integer id;
	
	private String username;
	
	private String password;

	private AccountProfileDto accountProfileDto;

	private  List<Order> orders;
	
	private Set<Role> roles;
}
