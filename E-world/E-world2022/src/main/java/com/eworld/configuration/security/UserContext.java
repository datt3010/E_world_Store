package com.eworld.configuration.security;

import com.eworld.entity.AccountRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContext implements UserDetails {

    private Collection<GrantedAuthority> authorities;
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
    List<AccountRole> accountRoleList;

    private String email;

    public UserContext(OAuth2User socialUser){
        this.username = socialUser.getName();
        this.email = socialUser.getAttribute("email");
        this.firstName = socialUser.getAttribute("family_name");
        this.lastName = socialUser.getAttribute("given_name");
    }

}
