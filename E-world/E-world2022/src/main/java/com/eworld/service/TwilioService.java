package com.eworld.service;

import com.eworld.configuration.security.UserContext;
import com.eworld.entity.Account;
import com.eworld.entity.Order;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TwilioService {

    public Mono<UserContext> sendOTPForPasswordReset(UserContext userContext);

    public Mono<String> validateOTP(String otp, String username);

    public Mono<UserContext> sendNotifation(Account account, Order order);

    public Mono<List<Account>> sendNotify();
}
