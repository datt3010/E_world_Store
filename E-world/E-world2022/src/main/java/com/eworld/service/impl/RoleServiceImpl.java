package com.eworld.service.impl;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
import com.eworld.projector.CustomerProjector;
import com.eworld.repository.customer.CustomerRepository;
import com.eworld.repository.role.RoleRepository;
import com.eworld.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Page<CustomerDto> listUser(CustomerFilter filter, Pageable pageable) {
        Page<Account> page = roleRepository.findPaging(filter,pageable);
        List<CustomerDto> content = CustomerProjector.convertToPageDto(page.getContent());
        return new PageImpl<>(content,pageable,page.getTotalElements());
    }
}
