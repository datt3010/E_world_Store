package com.eworld.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eworld.dto.CustomerDto;
import com.eworld.dto.CustomerInput;
import com.eworld.dto.CustomerUpdate;
import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
import com.eworld.projector.CustomerProjector;
import com.eworld.repository.CustomerRepository;
import com.eworld.repository.RoleRepository;
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
				.username(input.getUsername())
				.password(input.getPassword())
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
		
//		List<Role> roles = roleRepository.findByIdIn(input.getRoleIds());
//		Set<AccountRole> accountRoles = roles.stream()
//				.map(e -> AccountRole.builder()
//					.account(account)
//					.role(e)
//					.accountId(input.getId())
//					.roleId(3)
//					.build())
//				.collect(Collectors.toSet());
//		account.setAccountRoles(accountRoles);
		
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
	@Transactional
	public void delete(Integer id) {
		customerRepo.deleteById(id);
	}

	@Override
	@Transactional
	public CustomerDto update(Integer id, CustomerUpdate input) {
		
		Instant instant = Instant.now();
		Date date = Date.from(instant);
		
		Account account = findById(id);
		account.setCreateAt(date);
		account.setUsername(input.getUsername());
		account.setPassword(input.getPassword());
		account.setFirstName(input.getFirstName());
		account.setLastName(input.getLastName());
		account.setAge(input.getAge());
		account.setDateOfBirth(input.getDateOfBirth());
		account.setAddress(input.getAddress());
		account.setEmail(input.getEmail());
		account.setGioitinh(input.getGioitinh());
		account.setStatus(input.getStatus());
		account.setImage(input.getLogo());
		customerRepo.save(account);
	
		return CustomerDto.builder().id(id).build();
		
	}
}
