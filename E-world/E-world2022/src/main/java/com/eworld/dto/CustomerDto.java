package com.eworld.dto;

import java.util.Date;
import java.util.List;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.entity.AccountRole;
import com.eworld.entity.Order;
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
	
	private String email;
	
	private String phone;
	
	private String firstName;
	
	private String lastName;
	
	private Gender gioitinh;
	
	private Integer age;
	
	private Date dateOfBirth;
	
	private String address;
	
	private String nationality;
	
	private String logo;
	
	private UserStatus status;
	
	private  List<Order> orders;
	
	private List<AccountRole> roles;
}
