package com.eworld.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
import com.eworld.schema.CustomerDto;
import com.eworld.schema.CustomerInput;
public interface CustomerService {
	
	public Account findById(Integer id);
	
	public Page<CustomerDto> findPaging(CustomerFilter filter, Pageable pageable);
	
	public CustomerDto create(CustomerInput input);
	
	public CustomerDto getDetails(Integer id);
	
	public void delete(Integer id);

}
