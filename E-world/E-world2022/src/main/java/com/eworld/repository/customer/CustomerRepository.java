package com.eworld.repository.customer;

import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import com.eworld.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Account, Integer>,CustomerCustomRepository {

	@Query("DELETE FROM Account a WHERE a.username = ?1")
	public void deleteByUsername(String username);
	
	@Query("FROM Account a WHERE a.username =?1")
	public boolean existsById(String username);
	@Query("FROM Account a WHERE a.username LIKE :keyword OR email LIKE :keyword")
	public Account findByUsernameOrEmail(@Param(value = "keyword") String username);

	public Account findByUsername(String username);

	@Query("FROM Account a WHERE a.status LIKE :status")
	public List<Account> listAccountByStatus(@Param( value = "status") UserStatus status);

	@Query("UPDATE Account SET status= :status WHERE id=:accountId")
	@Modifying
	public void changeStatus(@Param(value = "status")UserStatus status, @Param(value = "accountId") Integer id);

	@Query("SELECT a FROM Account a INNER JOIN Order o ON o.account.id = a.id"
	     +" WHERE MONTH(o.createdAt) = :month AND o.account.id IN"
	     +" (SELECT DISTINCT o.account.id FROM Order o)"
	     +" GROUP BY a.id,a.address,a.age,a.createAt,a.dateOfBirth,a.email,a.firstName,a.gioitinh,a.image,a.lastName,a.nationality,a.password,a.phone,a.status,a.username"
	     +" ORDER BY SUM(o.totalPrice) DESC")
	public List<Account> listAccountTotalPrice(@Param(value = "month") Integer month);
}
