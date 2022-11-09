package com.bsuir.kursach.controller;

import com.bsuir.kursach.entity.User;
import com.bsuir.kursach.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

    private final UserService userService;

    @Autowired
    public AdministrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String checkAdminAndShow(HttpSession session, Model theModel) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getFirstName().equals("admin")) {
            List<User> userList = userService.getAllUsers();
            theModel.addAttribute("userList", userList);
            return "userList";
        } else {
            theModel.addAttribute("noAdminLoggedIn", "You must be logged in as an admin to use administration tab");
            return "hostelStatus";
        }
    }

    @GetMapping("/registerUser")
    public String showRegisterForm(Model theModel) {
        User user = new User();
        theModel.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerNewUser(@Valid @ModelAttribute("user") User newUser, BindingResult bindingResult, Model theModel) {
        if (bindingResult.hasErrors()) {
            theModel.addAttribute("user", newUser);
            return "register";
        } else {
            userService.saveUser(newUser);
            return "redirect:/administration/";
        }
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("tempUserId") long id) {
        userService.deleteUser(id);
        return "redirect:/administration/";
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("tempUserId") long id, Model theModel) {
        User user = userService.getUser(id);
        theModel.addAttribute("user", user);
        return "register";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
