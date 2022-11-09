package com.bsuir.kursach.controller;

import com.bsuir.kursach.entity.User;
import com.bsuir.kursach.servise.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String verifyLogin(@RequestParam String login, @RequestParam String password,
                              HttpSession session, Model theModel) {
        User user = loginService.loginUser(login, password);
        if (user == null) {
            theModel.addAttribute("loginError", "Error login in. Please try again");
            return "login";
        }
        session.setAttribute("loggedInUser", user);
        return "redirect:/hotelStatus/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/";
    }
}
