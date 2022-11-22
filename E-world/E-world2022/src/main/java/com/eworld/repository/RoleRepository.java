package com.eworld.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eworld.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	public List<Role> findByIdIn(Collection<Integer> ids);
}
