package com.bsuir.kursach.controller;

import com.bsuir.kursach.entity.Guest;
import com.bsuir.kursach.entity.Room;
import com.bsuir.kursach.servise.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public String roomList(Model theModel) {
        List<Room> vacantRooms = roomService.getVacantRooms();
        theModel.addAttribute("roomList", vacantRooms);
        return "roomList";
    }

    @GetMapping("/occupiedRoomlist")
    public String occupiedRoomList(Model theModel) {
        List<Room> occupiedRooms = roomService.getOccupiedRooms();
        theModel.addAttribute("roomList", occupiedRooms);
        return "occupiedRoomList";
    }

    @GetMapping("/checkout")
    public String checkoutRoom(@RequestParam("roomId") Integer theRoomId) {
        Room theRoom = roomService.getRoomById(theRoomId);
        List<Guest> occupants = theRoom.getOccupants();
        LocalDate localDate = LocalDate.now();
        localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        for (Guest guest : occupants) {
            guest.setCheckedout(true);
            guest.setCheckoutDate(localDate);
            theRoom.setOccupants(null);
            theRoom.setOccupied(false);
        }
        roomService.saveRoom(theRoom);
        return "redirect:/room/occupiedRoomlist";
    }
}