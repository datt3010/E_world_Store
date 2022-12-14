package com.eworld.dto.customer;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdate {

	private Date createdAt;
	
	@NotBlank(message = "{Account.firstName}")
	@Size(max = 100)
	private String firstName;
	
	@NotBlank(message = "{Account.lastName}")
	@Size(max = 100)
	private String lastName;
	
	@NotNull(message = "{Account.age.null}")
	@Min(value = 1, message = "{Account.age.min}")
	@Max(value = 200, message = "{Account.age.max}")
	private Integer age;
	
	@NotBlank(message = "{Account.address}")
	@Size(max = 255)
	private String address;
	
	@NotBlank(message = "{Account.nationality}")
	private String nationality;
	
	@NotNull(message = "{Account.dateOfBirth}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth ;
	
	@NotBlank(message = "{Account.email}")
	private String email;
	
	@NotBlank(message = "{Account.phone}")
	private String phone;
	
	@NotBlank(message = "{Account.username}")
	@Size(max = 100, min = 8, message = "{Size.Account.username.max}")
	private String username;
	
	@NotBlank(message = "{Account.password}")
	@Size(max = 100, message = "{Size.Account.password}")
	private String password;
	
	private Gender gioitinh;
	
	@NotNull(message = "{Account.status}")
	private UserStatus status;
	
	private String logo;
}
