package com.eworld.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
public class WebSecurityConfig{
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();

        http.authorizeHttpRequests()
                .antMatchers("/order/**", "/checkout/**","/doimatkhau","/admin/**").authenticated()
                .antMatchers("/admin/customer", "/admin/listcustomer").hasAnyRole("ADMIN", "STAFF")
                .antMatchers("/admin/staff/**", "/admin/liststaff/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .accessDeniedPage("/404");

//        http.formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/login");
        http.formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) ->{
                  response.sendRedirect("/login?error=true");
                    })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                  response.sendRedirect("/login?error=true");
                    });

        http.rememberMe().tokenValiditySeconds(30*24*60*60);

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .addLogoutHandler(new SecurityContextLogoutHandler());

        http.oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/oauth2/login/success",true)
                .failureUrl("/oauth2/login/failure")
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .and().tokenEndpoint()
                .accessTokenResponseClient(getToken());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/error", "/dist/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken(){
        return new DefaultAuthorizationCodeTokenResponseClient();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

}
