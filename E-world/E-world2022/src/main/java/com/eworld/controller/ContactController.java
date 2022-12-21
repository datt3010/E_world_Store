package com.eworld.controller;

import com.eworld.dto.email.EmailDto;
import com.eworld.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ContactController {

    @Autowired
    private EmailService emailService;

    @GetMapping("contact")
    public String contact(Model model) {
        model.addAttribute("email", new EmailDto());
        return "user/home/contact";
    }
    @PostMapping("contact")
    public String submitContact(Model model,
                                @ModelAttribute("email")@Validated EmailDto dto, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("message", "gửi thất bại");
            return "user/home/contact";
        }
        emailService.sendEmail(dto);
        model.addAttribute("message", "gửi email thành công");
        return "user/home/contact";
    }

}
