package com.eworld.schema;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	
	private Date dateOfBirth ;
	
	private String emai;
	
	private String phone;
	
	private String username;
	
	private String password;
	
	private String image;
	
	private Set<Integer> roleIds= new HashSet<>();
}
