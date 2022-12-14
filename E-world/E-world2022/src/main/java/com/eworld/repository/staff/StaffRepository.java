package com.eworld.repository.staff;

import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends JpaRepository<Account, Integer>,CustomStaffRepository {

    @Query("UPDATE Account SET status= :status WHERE id=:accountId")
    @Modifying
    public void changeStatus(@Param(value = "status") UserStatus status, @Param(value = "accountId") Integer id);
}
