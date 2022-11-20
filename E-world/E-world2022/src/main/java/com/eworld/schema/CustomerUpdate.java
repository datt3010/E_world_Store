package com.eworld.schema;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class CustomerUpdate {

	private Date createdAt;
	
	private String firstName;
	
	private String lastName;
	
	private Integer age;
	
	private String address;
	
	private String nationality;
	
	private Date dateOfBirth ;
	
	private String emai;
	
	private String phone;
}
