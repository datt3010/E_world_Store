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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Override
    @Transactional
    public UserContext create(UserContext input) {

        Instant instant = Instant.now();
        Date date = Date.from(instant);

        Role role = roleRepository.findById("2").orElseThrow();

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

        AccountRole accountRole = accountRoleRepository.findByAccountId(account.getId());
                account.setAccountRoles(Set.of(accountRole = AccountRole.builder()
                        .account(account)
                        .accountId(input.getId())
                        .role(role)
                        .roleId(role.getId())
                        .build()));

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

    @Override
    public UserContext createFormSocial(UserContext input) {

        Instant instant = Instant.now();
        Date date = Date.from(instant);
        Role role = roleRepository.findById("2").orElseThrow();

        Account account = Account.builder()
                .createAt(date)
                .username(input.getUsername())
                .password(input.getPassword())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .phone("0865057229")
                .gioitinh(Gender.Nam)
                .age(18)
                .dateOfBirth(date)
                .address("Go O Moi")
                .nationality("VN")
                .image("images.jpg")
                .status(UserStatus.ACTIVE)
                .build();

        AccountRole accountRole = accountRoleRepository.findByAccountId(account.getId());
        account.setAccountRoles(Set.of(accountRole = AccountRole.builder()
                .account(account)
                .accountId(input.getId())
                .role(role)
                .roleId(role.getId())
                .build()));
        customerRepository.save(account);
        return UserContext.builder().id(account.getId()).build();
    }

    @Override
    public boolean checkExistUser(String username) {
        Account account = customerRepository.findByUsername(username);
        if(StringUtils.isBlank(username)){
            return false;
        }
        if(!account.getUsername().equalsIgnoreCase(username)){
            return false;
        }
        return true;
    }
}
