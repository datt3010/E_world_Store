package com.eworld.configuration.security;

import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import com.eworld.repository.customer.CustomerRepository;
import com.eworld.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Service("sec")
public class UserContextService implements UserDetailsManager, Serializable {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    PasswordEncoder pe;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = customerRepository.findByUsernameOrEmail(username);

        return UserContext.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(pe.encode(account.getPassword()))
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .fullName(account.getFirstName() + " " +account.getLastName())
                .email(account.getEmail())
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

    public UserContext getUserContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object userContext = auth.getPrincipal();
        return userContext instanceof UserContext ? (UserContext) userContext : null;
    }

    public boolean isAuthenticated(){
        return this.getUserContext() != null;
    }

    public void setAccount(UserContext userContext) {
        Authentication auth = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public void createUser(UserDetails user) {
         accountService.create((UserContext) user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        UserContext userContext = getCurrent();
            if(oldPassword.equalsIgnoreCase(newPassword)){
                userContext.setPassword(oldPassword);
                accountService.changePassword(userContext);
            }
            else{
               userContext.setPassword(newPassword);
               accountService.changePassword(userContext);
            }
    }
    @Override
    public boolean userExists(String username) {
        return false;
    }
    public UserContext createFormSocial(OAuth2User socialUser){
        UserContext userContext = new UserContext(socialUser);
        accountService.create(userContext);
        return accountService.findbyUsernameOrEmail(userContext.getEmail());
    }
}