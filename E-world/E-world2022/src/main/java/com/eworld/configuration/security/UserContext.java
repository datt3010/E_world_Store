package com.eworld.configuration.security;

import com.eworld.entity.Account;
import com.eworld.entity.AccountRole;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContext implements UserDetails {

    private Account account;
    @NotBlank(message = "{Account.password}")
    @Size(min =4, max = 50, message = "{Account.password.size}")
    private String password;
    @NotBlank(message = "{Account.username}")
    @Size(min=4, max = 50, message = "{Account.username.size}")
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @NotBlank(message = "{Account.firstName}")
    @Size(min = 5, max = 60, message = "{Account.firstName.size}")
    private String firstName;
    @NotBlank(message = "{Account.lastName}")
    @Size(min = 5, max = 60, message = "{Account.lastName.size}")
    private String lastName;
    private String fullName;
    private Integer id;
    Set<AccountRole>  accountRoles;
    private String email;

    private String image;

    public UserContext(OAuth2User socialUser){
        this.username = socialUser.getName();
        this.email = socialUser.getAttribute("email");
        this.firstName = socialUser.getAttribute("family_name");
        this.lastName = socialUser.getAttribute("given_name");
    }

    boolean isStaff(){
        return (accountRoles !=null && !accountRoles.isEmpty());
    }

    boolean hasAnyRole(String...roles) {
        if (this.accountRoles == null || this.accountRoles.isEmpty()) {
            return false;
        }
        return accountRoles.stream().anyMatch(ur ->{
            return Stream.of(roles).anyMatch(role -> ur.getRole().getId().endsWith(role));
        });
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
    Set<AccountRole> userRoles = this.getAccountRoles();
		if(userRoles == null) {
        return Set.of();
    }
        return userRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName())).collect(Collectors.toSet());
}
}
