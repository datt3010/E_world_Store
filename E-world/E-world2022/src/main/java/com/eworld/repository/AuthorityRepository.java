package com.eworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eworld.entity.UserRole;

@Repository
public interface AuthorityRepository extends JpaRepository<UserRole, Long> {

}
