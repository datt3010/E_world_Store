package com.eworld.configuration.security;

import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import com.eworld.entity.AccountProfile;
import com.eworld.entity.AccountRole;
import com.eworld.repository.customer.CustomerRepository;
import com.eworld.repository.role.AccountRoleRepository;
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
import java.util.Set;

@Service("sec")
public class UserContextService implements UserDetailsManager, Serializable {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    PasswordEncoder pe;
    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = customerRepository.findByUsername(username);
        Set<AccountRole> accountRole = accountRoleRepository.listRoleByAccountId(account.getId());

        return UserContext.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(pe.encode(account.getPassword()))
                .account(Account.builder()
                        .accountProfile(AccountProfile.builder()
                                .firstName(account.getAccountProfile().getFirstName())
                                .lastName(account.getAccountProfile().getLastName())
                                .email(account.getAccountProfile().getEmail())
                                .image(account.getAccountProfile().getImage())
                                .build())
                        .build())
                .fullName(account.getAccountProfile().getFirstName() + " " +account.getAccountProfile().getLastName())
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountRoles(accountRole)
                .enabled(account.getAccountProfile().getStatus().equals(UserStatus.ACTIVE)?true :false)
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
            else {
                userContext.setPassword(newPassword);
                accountService.changePassword(userContext);
            }
    }
    @Override
    public boolean userExists(String username) {
        return accountService.checkExistUser(username);
    }
    public UserContext createFormSocial(OAuth2User socialUser){
        UserContext userContext = new UserContext(socialUser);
        accountService.createFormSocial(userContext);
        return accountService.findByUsername(userContext.getAccount().getAccountProfile().getEmail());
    }

    public boolean hasAnyRole(String ... roles){
        UserContext userContext = this.getUserContext();
        return userContext !=null && userContext.hasAnyRole(roles);
    }
    public UserContext getCurrentlyLoggedInCustomer(Authentication authentication){
        if(authentication !=null){
            return null;
        }
        UserContext userContext = null;
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserContext){
            userContext = ((UserContext) principal);
        }
        else if (principal instanceof  OAuth2User){
            String email = ((UserContext)principal).getAccount().getAccountProfile().getEmail();
            userContext = accountService.findByUsername(email);
        }
        return userContext;
    }
}