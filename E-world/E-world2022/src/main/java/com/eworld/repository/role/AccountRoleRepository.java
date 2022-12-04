package com.eworld.repository.role;

import com.eworld.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {
    @Query("FROM AccountRole ar WHERE ar.accountId =?1")
    public List<AccountRole> findByIdAccountID(Integer id);

}
