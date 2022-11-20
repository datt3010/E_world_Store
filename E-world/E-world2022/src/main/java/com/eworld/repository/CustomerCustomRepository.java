package com.eworld.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;

public interface CustomerCustomRepository {
	
	Page<Account> findPaging(CustomerFilter filter, Pageable pageable);
}
