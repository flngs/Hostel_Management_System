package com.bsuir.kursach.controller;

import com.bsuir.kursach.entity.Guest;
import com.bsuir.kursach.entity.Room;
import com.bsuir.kursach.servise.HostelStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/hotelStatus")
public class HostelStatusController {

    private final HostelStatusService hostelStatusService;

    @Autowired
    public HostelStatusController(HostelStatusService hostelStatusService) {
        this.hostelStatusService = hostelStatusService;
    }

    @GetMapping("/")
    public String showHotelStatus(Model theModel) {

        List<Guest> guestList = hostelStatusService.getActualGuests();
        List<Room> roomList = hostelStatusService.getAllRooms();

        Integer numberOfRooms = 0;
        Integer numberOfOccupiedRooms = 0;
        Integer numberOfVacantRooms = 0;

        for (Room theRoom : roomList) {
            numberOfRooms++;
            if (theRoom.getIsOccupied()) {
                numberOfOccupiedRooms++;
            } else {
                numberOfVacantRooms++;
            }

        }

        Integer numberOfGuests = 0;
        Integer upcommingCheckOuts = 0;

        for (Guest theGuest : guestList) {
            if (!theGuest.getIsCheckedout()) {
                numberOfGuests++;
            } else {
                if (theGuest.getCheckoutDate().isEqual(LocalDate.now()) || theGuest.getCheckoutDate().isBefore(
                        LocalDate.now())) {
                    upcommingCheckOuts++;
                }
            }
        }
        theModel.addAttribute("numberOfRooms", numberOfRooms);
        theModel.addAttribute("numberOfOccupiedRooms", numberOfOccupiedRooms);
        theModel.addAttribute("numberOfVacantRooms", numberOfVacantRooms);
        theModel.addAttribute("numberOfGuests", numberOfGuests);
        theModel.addAttribute("upcommingCheckOuts", upcommingCheckOuts);

        return "hostelStatus";
    }

}
