package com.eworld.configuration;

import com.eworld.configuration.security.UserContext;
import com.eworld.configuration.security.UserContextService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.eworld.configuration.security.TokenPayLoad;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserContextService userContextService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String token = null;
            String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7);
            }

            if (StringUtils.hasText(token) && token.split("\\.").length == 3) {
                String tokenPayload = token.split("\\.")[1];

                byte[] tokenPayloadByte = Base64.getDecoder().decode(tokenPayload);
                String decodedPayload = new String(tokenPayloadByte);
                TokenPayLoad payload = objectMapper.readValue(decodedPayload, TokenPayLoad.class);

                UserContext userContext = (UserContext) userContextService.loadUserByUsername(payload.getUsername());
                if (userContext.isEnabled() == false) {
                    throw new DisabledException("User is disabled");
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userContext, null, userContext.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }


    }
