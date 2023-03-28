package com.eworld.dto.customer;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eworld.dto.profile.AccountProfileDto;
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

	private AccountProfileDto accountProfileDto;

	@NotBlank(message = "{Account.username}")
	@Size(max = 100, min = 8, message = "{Size.Account.username.max}")
	private String username;

	@NotBlank(message = "{Account.password}")
	@Size(max = 100, message = "{Size.Account.password}")
	private String password;


}
