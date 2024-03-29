package com.eworld.configuration;

import com.eworld.configuration.security.UserContextService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserContextService userContextService;

    @Autowired
    private  JwtServiceProvider jwtServiceProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String jwt = getJwtFromRequest(request);
          try {
              if(StringUtils.hasText(jwt) && jwtServiceProvider.validateToken(jwt)) {
                  String userName = jwtServiceProvider.getUserNameFromJwt(jwt);
                  UserDetails userDetails = userContextService.loadUserByUsername(userName);
                  if (userDetails != null) {
                      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                  }
              }
          }
          catch (Exception e){
            logger.error("Failed on set user authentication",e);
          }
        filterChain.doFilter(request,response);
        }
        private String getJwtFromRequest(HttpServletRequest request){
            String bearToken = request.getHeader("Authorization");
            if(StringUtils.hasText(bearToken) && bearToken.startsWith("Bear ")){
                return bearToken.substring(7);
            }
            return null;
        }


    }
