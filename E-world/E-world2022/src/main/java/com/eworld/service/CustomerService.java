package com.eworld.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.dto.CustomerDto;
import com.eworld.dto.CustomerInput;
import com.eworld.dto.CustomerUpdate;
import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
public interface CustomerService {
	
	public Account findById(Integer id);
	
	public Page<CustomerDto> findPaging(CustomerFilter filter, Pageable pageable);
	
	public CustomerDto create(CustomerInput input);
	
	public CustomerDto getDetails(Integer id);
	
	public void delete(Integer id);
	
	public CustomerDto update(Integer id, CustomerUpdate input);

}
