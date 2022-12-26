package com.eworld.service.impl;

import com.eworld.contstant.UserStatus;
import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.customer.CustomerInput;
import com.eworld.dto.customer.CustomerUpdate;
import com.eworld.entity.Account;
import com.eworld.entity.AccountRole;
import com.eworld.entity.Role;
import com.eworld.filter.CustomerFilter;
import com.eworld.projector.CustomerProjector;
import com.eworld.repository.customer.CustomerRepository;
import com.eworld.repository.role.AccountRoleRepository;
import com.eworld.repository.role.RoleRepository;
import com.eworld.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AccountRoleRepository accountRoleRepository;

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

		Role role = roleRepository.findById("3").orElseThrow();
		
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

		AccountRole accountRole = accountRoleRepository.findByAccountId(account.getId());
		account.setAccountRoles(Set.of(accountRole = AccountRole.builder()
				.account(account)
				.accountId(input.getId())
				.role(role)
				.roleId(role.getId())
				.build()));

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
	public void changeStatus(Integer id) {
		customerRepo.changeStatus(UserStatus.INACTIVE,id);
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

	@Override
	public List<Account> listAccountTotalPrice(Integer month) {
		return customerRepo.listAccountTotalPrice(month);
	}

}
