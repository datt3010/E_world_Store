package com.eworld.service;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.customer.CustomerInput;
import com.eworld.dto.customer.CustomerUpdate;
import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface CustomerService {

	public Account findById(Integer id);
	
	public Page<CustomerDto> findPaging(CustomerFilter filter, Pageable pageable);
	
	public CustomerDto create(CustomerInput input);
	
	public CustomerDto getDetails(Integer id);

	public void changeStatus(Integer id);
	
	public CustomerDto update(Integer id, CustomerUpdate input);
	
	
}
