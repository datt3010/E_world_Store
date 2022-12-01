package com.eworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eworld.entity.Account;

public interface CustomerRepository extends JpaRepository<Account, Integer>,CustomerCustomRepository {
		
	@Query("FROM Account a WHERE a.username= ?1 OR a.email= ?1")
	Account findByUsernameAndEmail(String username);
	
	@Query("FROM Account a WHERE a.accountRoles IS NOT EMPTY")
	List<Account> findEmployees();
	
	@Query("DELETE FROM Account a WHERE a.username = ?1")
	public void deleteByUsername(String username);
	
	@Query("FROM Account a WHERE a.username =?1")
	public boolean existsById(String username);
}
