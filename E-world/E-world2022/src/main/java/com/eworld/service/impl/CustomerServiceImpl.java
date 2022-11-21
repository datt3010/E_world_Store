package com.eworld.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eworld.entity.Account;
import com.eworld.entity.AccountRole;
import com.eworld.entity.Role;
import com.eworld.filter.CustomerFilter;
import com.eworld.projector.CustomerProjector;
import com.eworld.repository.CustomerRepository;
import com.eworld.repository.RoleRepository;
import com.eworld.schema.CustomerDto;
import com.eworld.schema.CustomerInput;
import com.eworld.service.CustomerService;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Account findById(Integer id) {
		return customerRepo.findById(id).get();  
	}

	@Override
	public Page<CustomerDto> findPaging(CustomerFilter filter, Pageable pageable) {
		Page<Account> page = customerRepo.findPaging(filter, pageable);
		List<CustomerDto> content = CustomerProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	@Transactional
	public CustomerDto create(CustomerInput input) {
		
		
		Instant instant = Instant.now();
		
		Date date = Date.from(instant);
		
		Account account = Account.builder()
				.createAt(date)
				.username("sa")
				.password("123")
				.firstName(input.getFirstName())
				.lastName(input.getLastName())
				.age(input.getAge())
				.address(input.getAddress())
				.nationality(input.getNationality())
				.dateOfBirth(input.getDateOfBirth())
				.email(input.getEmail())
				.phone(input.getPhone())
				.image(input.getLogo())
				.status(input.getStatus())
				.gioitinh(input.getGioitinh())
				.build();
		
		List<Role> roles = roleRepository.findByIdIn(input.getRoleIds());
		Set<AccountRole> accountRoles = roles.stream()
				.map(e -> AccountRole.builder()
					.account(account)
					.role(e)
					.build())
				.collect(Collectors.toSet());
		account.setAccountRoles(accountRoles);
		
		customerRepo.save(account);
		return CustomerDto.builder()
				.id(account.getId())
				.build();
	}

	@Override
	public CustomerDto getDetails(Integer id) {
		
		Account account = customerRepo.findById(id).get();
		CustomerDto dto = CustomerProjector.convertToDetailDto(account);
		
		return dto;
	}

	@Override
	@Transactional()
	public void delete(Integer id) {
		customerRepo.deleteById(id);
	}
	
}
