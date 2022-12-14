package com.eworld.repository.customer;

import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Account, Integer>,CustomerCustomRepository {

	@Query("DELETE FROM Account a WHERE a.username = ?1")
	public void deleteByUsername(String username);
	
	@Query("FROM Account a WHERE a.username =?1")
	public boolean existsById(String username);
	@Query("FROM Account a WHERE a.username LIKE :keyword OR email LIKE :keyword")
	public Account findByUsernameOrEmail(@Param(value = "keyword") String username);

	public Account findByUsername(String username);

	@Query("UPDATE Account SET status= :status WHERE id=:accountId")
	@Modifying
	public void changeStatus(@Param(value = "status")UserStatus status, @Param(value = "accountId") Integer id);
}
