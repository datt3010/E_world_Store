package com.eworld.repository.staff;

import com.eworld.entity.Account;
import com.eworld.filter.StaffFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomStaffRepository  {
    Page<Account> findPaging(StaffFilter filter, Pageable pageable);
}
