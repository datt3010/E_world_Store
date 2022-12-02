package com.eworld.configuration.security;

import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import com.eworld.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserContextService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder pe;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = customerRepository.findByUsername(username);
        return UserContext.builder()
                .username(account.getUsername())
                .password(pe.encode(account.getPassword()))
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(account.getStatus().equals(UserStatus.ACTIVE)?true :false)
                .build();
    }

    public Optional<UserContext> findCurrent(){
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof  UserContext)
                .map(UserContext.class::cast);
    }

    public UserContext getCurrent(){
        return findCurrent()
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("No current user associated with this request"));
    }
}