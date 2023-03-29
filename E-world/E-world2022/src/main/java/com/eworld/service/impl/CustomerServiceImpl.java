package com.eworld.service.impl;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.customer.CustomerInput;
import com.eworld.dto.customer.CustomerUpdate;
import com.eworld.entity.Account;
import com.eworld.entity.AccountProfile;
import com.eworld.entity.AccountRole;
import com.eworld.entity.Role;
import com.eworld.filter.CustomerFilter;
import com.eworld.projector.CustomerProjector;
import com.eworld.repository.customer.CustomerProfileRepository;
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

	@Autowired
	private CustomerProfileRepository customerProfileRepository;

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
				.build();
		AccountProfile accountProfile = AccountProfile.builder()
				.account(account)
				.firstName(input.getAccountProfileDto().getFirstName())
				.lastName(input.getAccountProfileDto().getLastName())
				.email(input.getAccountProfileDto().getEmail())
				.phone(input.getAccountProfileDto().getPhone())
				.gioitinh(input.getAccountProfileDto().getGioitinh())
				.dateOfBirth(input.getAccountProfileDto().getDateOfBirth())
				.address(input.getAccountProfileDto().getAddress())
				.nationality(input.getAccountProfileDto().getNationality())
				.image(input.getAccountProfileDto().getLogo())
				.status(input.getAccountProfileDto().getStatus())
				.build();
		account.setAccountProfile(accountProfile);

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
		AccountProfile accountProfile = customerProfileRepository.findByAccountId(account.getId());
		account.setCreateAt(date);
		account.setPassword(input.getPassword());

		accountProfile = AccountProfile.builder()
				.firstName(input.getAccountProfileDto().getFirstName())
				.lastName(input.getAccountProfileDto().getLastName())
				.dateOfBirth(input.getAccountProfileDto().getDateOfBirth())
				.address(input.getAccountProfileDto().getAddress())
				.email(input.getAccountProfileDto().getEmail())
				.gioitinh(input.getAccountProfileDto().getGioitinh())
				.status(input.getAccountProfileDto().getStatus())
				.image(input.getAccountProfileDto().getLogo())
				.build();
		account.setAccountProfile(accountProfile);
		customerRepo.save(account);
		return CustomerDto.builder().id(id).build();
	}

	@Override
	public List<Account> listAccountTotalPrice(Integer month) {
		return customerRepo.listAccountTotalPrice(month);
	}

	public boolean checkPatternPhone(String phone){
		String pattern = "^0(3[2-9]|5[689]|7[0|6-9]|8[1-5]|9[0|3|4|5|7|8])\\d{7}$";
		return phone.matches(pattern);
	}
}
