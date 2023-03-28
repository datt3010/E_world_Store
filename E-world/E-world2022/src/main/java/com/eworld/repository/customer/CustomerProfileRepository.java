package com.eworld.repository.customer;

import com.eworld.entity.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfileRepository extends JpaRepository<AccountProfile, Integer> {
      AccountProfile findByAccountId(Integer id);
}
