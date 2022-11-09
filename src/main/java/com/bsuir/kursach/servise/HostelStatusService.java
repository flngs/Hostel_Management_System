package com.bsuir.kursach.servise;

import com.bsuir.kursach.entity.Guest;
import com.bsuir.kursach.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostelStatusService {

    private final RoomService roomService;
    private final GuestService guestService;

    @Autowired
    public HostelStatusService(RoomService roomService, GuestService guestService) {
        this.roomService = roomService;
        this.guestService = guestService;
    }

    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    public List<Guest> getActualGuests() {
        return guestService.getActualGuests();
    }
}
