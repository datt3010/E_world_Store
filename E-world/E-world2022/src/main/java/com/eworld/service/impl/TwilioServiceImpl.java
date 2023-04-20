package com.eworld.service.impl;

import com.eworld.configuration.TwilioConfig;
import com.eworld.configuration.security.UserContext;
import com.eworld.entity.Account;
import com.eworld.entity.Order;
import com.eworld.repository.customer.CustomerRepository;
import com.eworld.service.TwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
@Service
@Slf4j
public class TwilioServiceImpl implements TwilioService {

    Map<String, String> otpMap= new HashMap<>();
    @Autowired
    private TwilioConfig twilioConfig;

    Logger logger;

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Mono<UserContext> sendOTPForPasswordReset(UserContext userContext) {
        try {
            Account account = userContext.getAccount();
            String phoneFormat = "+84" +userContext.getAccount().getAccountProfile().getPhone().substring(1);
            PhoneNumber to = new PhoneNumber(phoneFormat);
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = generateOTP();
            account.setPassword("eworld"+otp);
            String newPassword = account.getPassword();
            customerRepository.save(account);
            String otpMessage = "Dear customer, Your newPassword is ##" + newPassword + "##.This newPassword to complete your transaction.Please do not share this information with anybody thank you";
            Message message = Message.creator(to,from,otpMessage).create();
            otpMap.put(userContext.getUsername(), otp);
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return Mono.just(userContext);
    }

    @Override
    public Mono<String> validateOTP(String otp, String username) {
        if(otp.equals(otpMap.get(username))){
            return Mono.just("Valid OTP please process with your transaction");
        }
        else{
            return Mono.error(new IllegalArgumentException("Invalid otp please retry"));
        }
    }

    @Override
    public Mono<UserContext> sendNotifation(Account account, Order order) {
        String phoneFormat = "+84" +account.getAccountProfile().getPhone().substring(1);
        PhoneNumber to = new PhoneNumber(phoneFormat);
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        String otpMessage = "Mã đơn hàng:" + order.getId()+ " đã xác nhận thành công, cảm ơn bạn đã trải nghiệm mua sắm tại eworld";
        Message message = Message.creator(to,from,otpMessage).create();
        return Mono.just(UserContext.builder().account(account).build());
    }

    @Override
    public Mono<List<Account>> sendNotify() {
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        String body,phoneFormat;
        List<Account> accountList = customerRepository.findAll();
        for(Account account :accountList){
             phoneFormat = "+84" +account.getAccountProfile().getPhone().substring(1);
             PhoneNumber to = new PhoneNumber(phoneFormat);
             body = "Hi " + account.getAccountProfile().getFirstName() + " Eworld đang có chương trình khuyến mãi";
            Message message = Message.creator(to,from,body).create();
        }
        return Mono.just(accountList);
    }


    private String generateOTP(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
