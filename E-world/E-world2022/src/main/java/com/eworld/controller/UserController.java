package com.eworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@RequestMapping("/login/error")
	public String error(Model model) {
		model.addAttribute("message","Error Login");
		return "/dangnhap";
	}
}
