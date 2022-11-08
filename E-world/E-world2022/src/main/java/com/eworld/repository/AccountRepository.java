package com.eworld.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eworld.entity.Account;


@Repository

public interface AccountRepository extends JpaRepository<Account, Long> {
		
	Optional<Account> findByUsername(  String username);
	
	@Query("FROM Account a WHERE a.username=?1")
	Account findUsername( String username);
}
