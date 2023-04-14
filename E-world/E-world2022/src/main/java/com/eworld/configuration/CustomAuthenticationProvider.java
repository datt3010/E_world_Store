package com.eworld.configuration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider  extends DaoAuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            return super.authenticate(authentication);
        } catch (InternalAuthenticationServiceException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
