package com.eworld.repository.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eworld.entity.Account;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Account, Integer>,CustomerCustomRepository {

	@Query("DELETE FROM Account a WHERE a.username = ?1")
	public void deleteByUsername(String username);
	
	@Query("FROM Account a WHERE a.username =?1")
	public boolean existsById(String username);
	@Query("FROM Account a WHERE a.username LIKE :keyword OR email LIKE :keyword")
	public Account findByUsernameOrEmail(@Param(value = "keyword") String username);

	public Account findByUsername(String username);
}
