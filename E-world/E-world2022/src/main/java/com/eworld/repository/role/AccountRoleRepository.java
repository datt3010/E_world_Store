package com.eworld.repository.role;

import com.eworld.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {
    @Query("FROM AccountRole ar INNER JOIN Role r ON ar.roleId = r.id"
            +" WHERE ar.accountId = :idAccount AND  :idAccount IN ( SELECT id FROM Account)")
    Set<AccountRole> listRoleByAccountId(@Param(value = "idAccount") Integer accountId);
    AccountRole findByAccountId(Integer accountId);

    @Query("DELETE FROM AccountRole ar WHERE ar.accountId = :accountId")
    @Modifying
    public void deleteByAccountId(@Param(value = "accountId") Integer accountId);
}
