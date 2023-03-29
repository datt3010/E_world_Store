package com.eworld;

import com.eworld.configuration.TwilioConfig;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class EWorld2022Application {
	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void initTwilio(){
		Twilio.init(twilioConfig.getAccountId(), twilioConfig.getAuthToken());
	}

	public static void main(String[] args) {
		SpringApplication.run(EWorld2022Application.class, args);
	}
}
