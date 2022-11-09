package com.bsuir.kursach.controller;

import com.bsuir.kursach.entity.Guest;
import com.bsuir.kursach.entity.Room;
import com.bsuir.kursach.servise.GuestService;
import com.bsuir.kursach.servise.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/guest")
public class GuestController {

    private final GuestService guestService;
    private final RoomService roomService;

    @Autowired
    public GuestController(GuestService guestService, RoomService roomService) {
        this.guestService = guestService;
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public String guestList(Model theModel) {
        List<Guest> guestList = guestService.getActualGuests();
        theModel.addAttribute("guestList", guestList);
        return "guestList";
    }

    @GetMapping("/showAddGuest")
    public String showAddGuest(Model theModel) {
        Guest guest = new Guest();
        List<Room> vacantRooms = roomService.getVacantRooms();
        Room firstRoomOnList = vacantRooms.get(0);
        vacantRooms.remove(firstRoomOnList);
        LinkedHashMap<String, Room> vacantRoomsMap = populateRoomsMap(vacantRooms);

        theModel.addAttribute("guest", guest);
        theModel.addAttribute("roomsMap", vacantRoomsMap);
        theModel.addAttribute("selectedRoom", firstRoomOnList); //the room to be shown as first on the list
        return "addGuestForm";
    }

    @PostMapping("/saveGuest")
    public String saveGuest(@Valid @ModelAttribute("guest") Guest theGuest, BindingResult bindingResult, Model theModel) {
        if (bindingResult.hasErrors()) {
            List<Room> vacantRooms = roomService.getVacantRooms();
            Room firstRoomOnList = vacantRooms.get(0);
            LinkedHashMap<String, Room> vacantRoomsMap = populateRoomsMap(vacantRooms);
            theModel.addAttribute("roomsMap", vacantRoomsMap);
            theModel.addAttribute("selectedRoom", firstRoomOnList);
            return "addGuestForm";
        } else {
            theGuest.getRoom().setOccupied(true);
            theGuest.setCheckinDate(LocalDate.now());
            roomService.saveRoom(theGuest.getRoom());
            guestService.saveGuest(theGuest);
            return "redirect:/guest/list";

        }
    }

    @GetMapping("/checkout")
    public String checkoutGuest(@RequestParam("guestId") int theId, Model theModel) {
        Guest theGuest = guestService.getGuestById(theId);
        Room theRoom = roomService.getRoomById(theGuest.getRoom().getId());
        LocalDate localDate = LocalDate.now();
        localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        theGuest.setCheckoutDate(localDate);
        theGuest.setCheckedout(true);
        theGuest.setRoom(null);
        theGuest.setLastCheckedoutRoom(theRoom);
        theRoom.getOccupants().remove(theGuest);
        if (theRoom.getOccupants().size() == 0) {
            theRoom.setOccupied(false);
        }

        guestService.saveGuest(theGuest);
        roomService.saveRoom(theRoom);
        return "redirect:/guest/list";
    }

    @GetMapping("/update")
    public String updateGuest(@RequestParam("guestId") int theId, Model theModel) {
        Guest theGuest = guestService.getGuestById(theId);

        List<Room> vacantRooms = roomService.getVacantRooms();
        Room selectedRoom = theGuest.getRoom(); // previously selected room to be shown as first on the list
        vacantRooms.remove(selectedRoom);
        LinkedHashMap<String, Room> vacantRoomsMap = populateRoomsMap(vacantRooms);

        theModel.addAttribute("guest", theGuest);
        theModel.addAttribute("roomsMap", vacantRoomsMap);
        theModel.addAttribute("selectedRoom", selectedRoom);
        return "addGuestForm";
    }

    @GetMapping("/checkin")
    public String checkinGuestToSpecificRoom(@RequestParam("roomId") long theRoomId, Model theModel) {
        Guest theGuest = new Guest();
        Room selectedRoom = roomService.getRoomById(theRoomId);
        theModel.addAttribute("guest", theGuest);
        theModel.addAttribute("selectedRoom", selectedRoom);
        return "addGuestForm";
    }

    @GetMapping("/archivedGuestsList")
    public String archivedGuests(Model theModel) {

        List<Guest> guestList = guestService.getArchivedGuests();
        theModel.addAttribute("guestList", guestList);

        return "archivedGuestsList";

    }


    @GetMapping("/checkInToOccupiedRoom")
    public String checkInToOccupiedRoom(Model theModel) {
        Room room = null;
        Guest guest = new Guest();
        LinkedHashMap<String, Room> occupiedRoomsMap = null;
        List<Room> occupiedRooms = roomService.getOccupiedRooms();
        if (!occupiedRooms.isEmpty()) {
            room = occupiedRooms.get(0);
            occupiedRoomsMap = populateRoomsMap(occupiedRooms);
        }

        theModel.addAttribute("selectedRoom", room); // the room to be shown as first on the list
        theModel.addAttribute("guest", guest);
        theModel.addAttribute("roomsMap", occupiedRoomsMap);
        return "addGuestForm";
    }

    @GetMapping("/bill")
    public String showBill(@RequestParam("guestId") int id, Model theModel) {
        Guest guest = guestService.getGuestById(id);
        Room room;
        int nightsNumber = guestService.getNightsNumber(guest);

        if(guest.getIsCheckedout()) {
            room = guest.getLastCheckedoutRoom();
        }else {
            room = guest.getRoom();
        }

        double rate = room.getRate();
        theModel.addAttribute("guest", guest);
        theModel.addAttribute("room", room);
        theModel.addAttribute("nightsNumber", nightsNumber);
        theModel.addAttribute("rate",rate);
        theModel.addAttribute("tax", Room.TAX*rate*nightsNumber);
        theModel.addAttribute("total", nightsNumber*rate*(1+Room.TAX));
        return "guestBill";
    }

    private LinkedHashMap<String, Room> populateRoomsMap(List<Room> rooms) {
        if (rooms == null || rooms.size() == 0) {
            rooms.add(new Room(0, "No vacant rooms", true, null));
        }
        LinkedHashMap<String, Room> roomsMap = new LinkedHashMap<String, Room>();
        for (Room room : rooms) {
            roomsMap.put(Long.toString(room.getId()), room);
        }
        return roomsMap;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}