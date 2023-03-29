package com.eworld.service.impl;

import com.eworld.configuration.JwtServiceProvider;
import com.eworld.configuration.security.UserContext;
import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import com.eworld.entity.AccountProfile;
import com.eworld.entity.AccountRole;
import com.eworld.entity.Role;
import com.eworld.repository.customer.CustomerProfileRepository;
import com.eworld.repository.customer.CustomerRepository;
import com.eworld.repository.role.AccountRoleRepository;
import com.eworld.repository.role.RoleRepository;
import com.eworld.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Random;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServiceProvider jwtServiceProvider;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

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
                .build();

        AccountProfile accountProfile = AccountProfile.builder()
                        .firstName(input.getAccount().getAccountProfile().getFirstName())
                        .lastName(input.getAccount().getAccountProfile().getLastName())
                        .image(input.getAccount().getAccountProfile().getImage())
                        .status(UserStatus.ACTIVE)
                        .email(input.getAccount().getAccountProfile().getEmail())
                        .address(null)
                        .nationality("VN")
                        .phone(null)
                        .dateOfBirth(date)
                        .account(account)
                        .build();

        AccountRole accountRole = accountRoleRepository.findByAccountId(account.getId());
                account.setAccountRoles(Set.of(accountRole = AccountRole.builder()
                        .account(account)
                        .accountId(input.getId())
                        .role(role)
                        .roleId(role.getId())
                        .build()));

        account.setAccountProfile(accountProfile);
        input.setAccount(account);
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
    public UserContext findByUsername(String username) {
        Account account = customerRepository.findByUsername(username);
        return UserContext.builder().username(account.getUsername()).build();
    }

    @Override
    public UserContext createFormSocial(UserContext input) {

        Instant instant = Instant.now();
        Date date = Date.from(instant);
        Role role = roleRepository.findById("2").orElseThrow();
        int randomNumber = new Random().nextInt(9000)+1000;

        Account account = Account.builder()
                .createAt(date)
                .username(input.getUsername())
                .password("e_world" + randomNumber)
                .build();
        AccountProfile accountProfile = AccountProfile.builder()
                .account(account)
                .firstName(input.getAccount().getAccountProfile().getFirstName())
                .lastName(input.getAccount().getAccountProfile().getLastName())
                .email(input.getAccount().getAccountProfile().getEmail())
                .phone("0865057229")
                .gioitinh(Gender.Nam)
                .dateOfBirth(date)
                .address("Go O Moi")
                .nationality("VN")
                .image("images.jpg")
                .status(UserStatus.ACTIVE)
                .build();
        account.setAccountProfile(accountProfile);

        AccountRole accountRole = accountRoleRepository.findByAccountId(account.getId());
        account.setAccountRoles(Set.of(accountRole = AccountRole.builder()
                .account(account)
                .accountId(input.getId())
                .role(role)
                .roleId(role.getId())
                .build()));

        input.setAccount(account);
        customerRepository.save(account);
        return UserContext.builder().id(account.getId()).account(account).build();
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

    @Override
    public String handleTokenJwt(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtServiceProvider.generateToken((UserContext) authentication.getPrincipal());

        return jwt;
    }

    @Override
    public UserContext getByUserName(String username) {
        Account account = customerRepository.findByUsername(username);
        return UserContext.builder().account(account).build();
    }
}
