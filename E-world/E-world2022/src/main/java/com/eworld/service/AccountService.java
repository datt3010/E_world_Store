package com.eworld.service;

import com.eworld.configuration.security.UserContext;

public interface AccountService {

   public UserContext create(UserContext input);

   public UserContext findbyId(Integer id);

   public UserContext changePassword(UserContext userContext);

   public UserContext findByUsername(String username);

   public UserContext createFormSocial(UserContext input);

   public boolean checkExistUser(String username);

   public String handleTokenJwt(String username, String password);
}
