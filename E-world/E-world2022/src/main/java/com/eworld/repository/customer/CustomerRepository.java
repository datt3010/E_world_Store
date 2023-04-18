package com.eworld.repository.customer;

import com.eworld.contstant.UserStatus;
import com.eworld.dto.profile.AccountProfileDto;
import com.eworld.entity.Account;
import com.eworld.entity.AccountProfile;
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

	public Account findByUsername(String username);

	@Query("FROM Account a JOIN AccountProfile ac on ac.accountId = a.id WHERE ac.status LIKE :status")
	public List<Account> listAccountByStatus(@Param( value = "status") UserStatus status);

	@Query("UPDATE AccountProfile SET status= :status WHERE id=:accountId")
	@Modifying
	public void changeStatus(@Param(value = "status")UserStatus status, @Param(value = "accountId") Integer id);

	@Query("SELECT a FROM Account a INNER JOIN Order o ON o.account.id = a.id"
	     +" WHERE MONTH(o.createdAt) = MONTH(GETDATE()) AND o.account.id IN"
	     +" (SELECT DISTINCT o.account.id FROM Order o)"
	     +" GROUP BY a.id, a.accountProfile.address, a.createAt, a.accountProfile.dateOfBirth, a.accountProfile.email, a.accountProfile.firstName, a.accountProfile.gioitinh, a.accountProfile.image, a.accountProfile.lastName, a.accountProfile.nationality, a.password, a.accountProfile.phone, a.accountProfile.status, a.username"
	     +" ORDER BY SUM(o.totalPrice) DESC")
	public List<Account> listAccountTotalPrice(@Param(value = "month") Integer month);

	@Query("FROM AccountProfile ac JOIN Account a"
	      +" ON a.id = ac.accountId"
	      +" WHERE a.username LIKE :username")
	public AccountProfile getByUsername(@Param(value = "username") String username);
}
