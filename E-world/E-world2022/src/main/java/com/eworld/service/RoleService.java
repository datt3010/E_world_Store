package com.eworld.service;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.filter.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    public Page<CustomerDto> listUser(CustomerFilter filter, Pageable pageable);
}
