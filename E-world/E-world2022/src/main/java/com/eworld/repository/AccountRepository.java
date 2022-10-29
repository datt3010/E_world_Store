package com.eworld.repository;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import com.eworld.entity.Account;


@Repository
@Validated
public interface AccountRepository extends JpaRepository<Account, Long> {
		
	Optional<Account> findByUsername( @NotNull String username);
}
