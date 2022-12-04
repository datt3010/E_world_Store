package com.eworld.projector;

import java.util.List;
import java.util.stream.Collectors;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.entity.Account;

public class CustomerProjector {
	
	public static CustomerDto convertToPageDto(Account entity) {
		return CustomerDto.builder()
				.createdAt(entity.getCreateAt())
				.id(entity.getId())
				.firstName(entity.getFirstName())
				.lastName(entity.getLastName())
				.address(entity.getAddress())
				.username(entity.getUsername())
				.password(entity.getPassword())
				.email(entity.getEmail())
				.address(entity.getAddress())
				.dateOfBirth(entity.getDateOfBirth())
				.gioitinh(entity.getGioitinh())
				.phone(entity.getPhone())
				.nationality(entity.getNationality())
				.status(entity.getStatus())
				.logo(entity.getImage())
				.age(entity.getAge())
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
				.firstName(entity.getFirstName())
				.lastName(entity.getLastName())
				.age(entity.getAge())
				.address(entity.getAddress())
				.nationality(entity.getNationality())
				.dateOfBirth(entity.getDateOfBirth())
				.email(entity.getEmail())
				.phone(entity.getPhone())
				.logo(entity.getImage())
				.gioitinh(entity.getGioitinh())
				.status(entity.getStatus())
				.build();
	}
}
