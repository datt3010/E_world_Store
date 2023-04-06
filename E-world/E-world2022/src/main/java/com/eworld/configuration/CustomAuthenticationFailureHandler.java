package com.eworld.configuration;

import com.eworld.configuration.security.UserContext;
import com.eworld.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserContext userContext = accountService.findByUsername(username);
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            response.sendRedirect("/login?error=true");
            return;
        }
        if(userContext.getAccount() == null){
            request.getSession().setAttribute("message","username is not exists");
            response.sendRedirect("/login?username=true");
            return;
        }
    }
}
