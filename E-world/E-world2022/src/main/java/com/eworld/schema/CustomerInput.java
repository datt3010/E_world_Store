package com.eworld.schema;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInput {
	
	private Integer id;
	
	private Date createdAt;
	
	private String firstName;
	
	private String lastName;
	
	private Integer age;
	
	private String address;
	
	private String nationality;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth ;
	
	private String email;
	
	private String phone;
	
	private String username;
	
	private String password;
	
	private String logo;
	
	private UserStatus status;
	
	private Gender gioitinh;
	
	private Set<Integer> roleIds= new HashSet<>();
}
