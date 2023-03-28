package com.eworld.repository.role;

import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleCustomRepository {
    Page<Account> findPaging(CustomerFilter filter, Pageable pageable);

}
