package com.eworld.service;

import com.eworld.dto.email.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(EmailDto mail){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,"utf-8");
            mimeMessageHelper.setSubject(mail.getFullName()+ " " + " has sent email a message");
            mimeMessageHelper.setFrom(mail.getMailFrom(),"Client Feedback");
            mimeMessageHelper.setTo("datthuynh30102002@gmail.com");
            String content = "<p><b>Client name:</b> " + mail.getFullName() + "</p>";
            content+= "<p><b>Client Email:</b>" + mail.getMailFrom() + "</p>";
            content+= "<p><b>content: </b>" +mail.getMailContent() + "</p>";
            content+= "<hr><img src='cid:logoImage' style='width:200px;height:250px;'/>";
            mimeMessageHelper.setText(content,true);
            ClassPathResource resource = new ClassPathResource("/static/images/logo.png");
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        }
        catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
