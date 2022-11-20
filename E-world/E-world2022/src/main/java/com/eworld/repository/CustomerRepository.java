package com.eworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eworld.entity.Account;

public interface CustomerRepository extends JpaRepository<Account, Integer>,CustomerCustomRepository {
	
}
