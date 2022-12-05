package com.eworld.controller;

import com.eworld.configuration.security.UserContext;
import com.eworld.configuration.security.UserContextService;
import com.eworld.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
public class UserController {
    private final String URI_BASE = "/oauth2/authorization/";
    private final List<String> clients = List.of("Facebook", "Google", "GitHub");

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private UserContextService userContextService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String homeLogin() {
        return "user/login/login";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String executeLogin(Model model,
//                               @RequestParam(name = "username", required = false)String username,
//                               @RequestParam(name = "password", required = false)String password){
//        UserContext userContext = userContextService.getCurrent();
//        if((username).equalsIgnoreCase(userContext.getUsername()) || (password.equalsIgnoreCase(userContext.getPassword()))){
//            model.addAttribute("message", "username or password incorrect");
//            return "user/login/login";
//        }
//        return "/user/home/index";
//    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String homeLogout() {
        return "user/login/login";
    }

    @RequestMapping(value = "/dangky", method = RequestMethod.GET)
    public String homeRegisger(Model model) {
        model.addAttribute("user", new UserContext());
        return "user/login/register";
    }

    @RequestMapping(value = "/doimatkhau", method = RequestMethod.GET)
    public String homeChangePassword() {
        return "user/login/changepassword";
    }

    @RequestMapping(value = "/dangky", method = RequestMethod.POST)
    public String register(Model model,
                           @ModelAttribute("user") @Validated UserContext userContext,
                           BindingResult result,
                           @RequestParam(name = "confirmPassword", required = false) String confirmPassword) {

        String url = "user/login/register";

        if (result.hasErrors()) {
            return url;
        }

        if (!userContext.getPassword().equalsIgnoreCase(confirmPassword)) {
            model.addAttribute("errorPassword", "Xác nhận mật khẩu không khớp");
            return url;
        }

        userContextService.createUser(userContext);
        model.addAttribute("message", "Dang ky thanh cong");
        return url;
    }

    @RequestMapping(value = "/doimatkhau", method = RequestMethod.POST)
    public String changePassword(Model model,
                                 @RequestParam(name = "password", required = false) String password,
                                 @RequestParam(name = "confirmPassword", required = false) String confirmPassword) {

        String url = "user/login/changepassword";
        if (StringUtils.isBlank(password) && StringUtils.isBlank(confirmPassword)) {
            model.addAttribute("message", "password or confirm password is not empty");
            return url;
        }
        if (!password.equalsIgnoreCase(confirmPassword)) {
            model.addAttribute("errorPassword", "Xác nhận mật khẩu không khớp");
            return url;
        }
        userContextService.changePassword(password, confirmPassword);
        model.addAttribute("message", "Đổi mật khẩu thành công");
        return url;
    }

    @RequestMapping("/oauth2/login/success")
    public String oauth2LoginSuccess(OAuth2AuthenticationToken auth) {
        OAuth2User socialUser = auth.getPrincipal();
        String email = socialUser.getAttribute("email").toString();
        try {
            UserContext userContext = accountService.findbyUsernameOrEmail(email);
            userContextService.setAccount(userContext);
        } catch (Exception e) {
            UserContext userContext = userContextService.createFormSocial(socialUser);
            userContextService.setAccount(userContext);
        }
        return "redirect:/";
    }

    @RequestMapping("/oauth2/login/failure")
    public String oauth2LoginFailure(Model model) {
        model.addAttribute("message", "Login is failed");
        return "user/error/404";
    }
}
