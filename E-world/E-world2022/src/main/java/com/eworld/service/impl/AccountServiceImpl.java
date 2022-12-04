package com.eworld.service.impl;

import com.eworld.configuration.security.UserContext;
import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import com.eworld.entity.AccountRole;
import com.eworld.entity.Role;
import com.eworld.repository.customer.CustomerRepository;
import com.eworld.repository.role.AccountRoleRepository;
import com.eworld.repository.role.RoleRepository;
import com.eworld.service.AccountService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    @Transactional
    public UserContext create(UserContext input) {

        Instant instant = Instant.now();
        Date date = Date.from(instant);

        Account account = Account.builder()
                .createAt(date)
                .username(input.getUsername())
                .password(input.getPassword())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email("email@gmail.com")
                .phone("0909442487")
                .gioitinh(Gender.Nam)
                .age(18)
                .dateOfBirth(date)
                .address("Go O Moi")
                .nationality("VN")
                .image("images.jpg")
                .status(UserStatus.ACTIVE)
                .build();
        if(CollectionUtils.isNotEmpty(input.getAccountRoleList())){
            Role role = roleRepository.findById(3).orElseThrow();
           List<AccountRole> accountRoleList = accountRoleRepository.findByIdAccountID(input.getId()).stream()
                   .map(e -> AccountRole.builder()
                           .roleId("3")
                           .accountId(input.getId())
                           .role(role)
                           .account(account)
                           .build())
                   .collect(Collectors.toList());
           account.setAccountRoles(accountRoleList);
        }
        customerRepository.save(account);
        return UserContext.builder().id(account.getId()).build();
    }

    @Override
    public UserContext findbyId(Integer id) {
        Account account = customerRepository.findById(id).orElseThrow();
        return UserContext.builder().id(account.getId()).build();
    }

    @Override
    @Transactional
    public UserContext changePassword(UserContext userContext) {
        Account account = customerRepository.findById(userContext.getId()).orElseThrow();
        account.setPassword(userContext.getPassword());
        customerRepository.save(account);
        return  UserContext.builder().id(account.getId()).build();
    }

    @Override
    public UserContext findbyUsernameOrEmail(String username) {
        Account account = customerRepository.findByUsernameOrEmail(username);
        return UserContext.builder().username(account.getUsername()).build();
    }
}
