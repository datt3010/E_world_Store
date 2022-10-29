package com.eworld.config;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.eworld.entity.Account;
import com.eworld.entity.PersonalInformation;
import com.eworld.service.AccountService;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//	
//	@Autowired
//	private AccountService accountService;
//	
//	@Autowired
//	BCryptPasswordEncoder passswordEncoder;
//	
//	@Bean
//	public InMemoryUserDetailsManager userDetailService(PasswordEncoder passwordEncoder) {
//		
//				UserDetails user = User.withUsername("sa")
//										.password("123")
//										.roles("Staff")
//										.build();
//		
//				UserDetails admin = User.withUsername("sa2")
//										.password("2002")
//										.roles("Admin")
//										.build();
//		
//		return new InMemoryUserDetailsManager(user,admin);
//	}
//	
//		@Bean
//		public SecurityFilterChain filterChain( HttpSecurity http) throws Exception {
//					
//					http.authorizeRequests()
//		            .antMatchers("/chitietsanpham").hasRole("Admin")
//		            .antMatchers("/").hasAnyRole("Staff","User")
//		            .anyRequest().permitAll();
//					
//					http.formLogin()
//					.loginPage("/dangnhap")
//					.defaultSuccessUrl("/")
//					.failureUrl("/404")
//					.usernameParameter("username")
//					.passwordParameter("password");
//					
//					http.rememberMe()
//						.rememberMeParameter("rememberMe");
//					
//					http.logout()
//						.logoutUrl("/logout")
//						.logoutSuccessUrl("/")
//						.addLogoutHandler(new SecurityContextLogoutHandler());
//					
//				return http.build();
//		}
//		
//		@Bean
//		public BCryptPasswordEncoder getPasswordEncoder() {
//			return new BCryptPasswordEncoder();
//		}
//		
//		protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//			
//			auth.userDetailsService(username ->{
//				try {
//					Account account = accountService.findByUsername(username);
//					String password = passswordEncoder.encode(username)
//				}
//				catch (NoSuchElementException e) {
//					throw new UsernameNotFoundException(username + " not found");
//				}
//			});
//		}
	}
