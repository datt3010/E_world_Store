package com.eworld.projector;

import java.util.List;
import java.util.stream.Collectors;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.profile.AccountProfileDto;
import com.eworld.entity.Account;

public class CustomerProjector {
	
	public static CustomerDto convertToPageDto(Account entity) {
		return CustomerDto.builder()
				.createdAt(entity.getCreateAt())
				.id(entity.getId())
				.username(entity.getUsername())
				.password(entity.getPassword())
				.accountProfileDto(AccountProfileDto.builder()
						.accountId(entity.getId())
						.firstName(entity.getAccountProfile().getFirstName())
						.lastName(entity.getAccountProfile().getLastName())
						.logo(entity.getAccountProfile().getImage())
						.phone(entity.getAccountProfile().getPhone())
						.email(entity.getAccountProfile().getEmail())
						.status(entity.getAccountProfile().getStatus())
						.nationality(entity.getAccountProfile().getNationality())
						.gioitinh(entity.getAccountProfile().getGioitinh())
						.dateOfBirth(entity.getAccountProfile().getDateOfBirth())
						.address(entity.getAccountProfile().getAddress())
						.build())
				.build();
	}
	
	public static List<CustomerDto> convertToPageDto(List<Account> entities){
		return entities.stream()
				.map(e -> convertToPageDto(e))
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
