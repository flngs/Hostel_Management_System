package com.bsuir.kursach.controller;

import com.bsuir.kursach.entity.Guest;
import com.bsuir.kursach.servise.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/commingCheckouts")
public class ComingCheckoutsController {

    private final GuestService guestService;

    @Autowired
    public ComingCheckoutsController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("/list")
    public String comingCheckoutsList(Model theModel) {
        List<Guest> comingCheckouts = guestService.getComingCheckouts();
        theModel.addAttribute("guestList", comingCheckouts);
        return "guestList";

    }

}
