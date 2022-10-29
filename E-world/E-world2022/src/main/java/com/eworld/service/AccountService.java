package com.eworld.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eworld.entity.Account;
import com.eworld.repository.AccountRepository;


@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	public Account findByUsername(String username) {
		return accountRepository.findByUsername(username).get();
	}
}
