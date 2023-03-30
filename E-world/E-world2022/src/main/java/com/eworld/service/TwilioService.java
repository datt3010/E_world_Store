package com.eworld.service;
import com.eworld.configuration.security.UserContext;
import reactor.core.publisher.Mono;
public interface TwilioService {

    public Mono<UserContext> sendOTPForPasswordReset(UserContext userContext);

    public Mono<String> validateOTP(String otp, String username);
}
