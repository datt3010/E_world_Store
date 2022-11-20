package com.eworld.projector;

import com.eworld.entity.Account;
import com.eworld.schema.CustomerDto;
import java.util.List;
import java.util.stream.Collectors;

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
				.image(entity.getImage())
				.age(entity.getAge())
				.build();
	}
	
	public static List<CustomerDto> convertToPageDto(List<Account> entities){
		return entities.stream()
				.map(e -> convertToPageDto(e))
				.collect(Collectors.toList());
	}
}
