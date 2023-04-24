package com.eworld.projector;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.profile.AccountProfileDto;
import com.eworld.entity.Account;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerProjector {

	public static List<CustomerDto> convertToPageDto(List<Account> entities){
		return entities.stream()
				.map(e -> convertToDetailDto(e))
				.collect(Collectors.toList());
	}
	
	public static CustomerDto convertToDetailDto(Account entity) {
		return CustomerDto.builder()
				.createdAt(entity.getCreateAt())
				.username(entity.getUsername())
				.password(entity.getPassword())
				.accountProfileDto(AccountProfileDto.builder()
						.firstName(entity.getAccountProfile().getFirstName())
						.lastName(entity.getAccountProfile().getLastName())
						.status(entity.getAccountProfile().getStatus())
						.email(entity.getAccountProfile().getEmail())
						.phone(entity.getAccountProfile().getPhone())
						.nationality(entity.getAccountProfile().getNationality())
						.logo(entity.getAccountProfile().getImage())
						.dateOfBirth(entity.getAccountProfile().getDateOfBirth())
						.address(entity.getAccountProfile().getAddress())
						.gioitinh(entity.getAccountProfile().getGioitinh())
						.build())
				.build();
	}
}
