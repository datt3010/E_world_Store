package com.eworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String homeLogin(){
        return "user/login/login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(){
        return "user/home/index";
    }
}
