package com.bsuir.kursach.servise;

import com.bsuir.kursach.entity.Room;
import com.bsuir.kursach.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepo roomRepo;

    @Autowired
    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public List<Room> getAllRooms() {
        return (List<Room>) roomRepo.findAll();
    }

    public List<Room> getVacantRooms() {
        return roomRepo.findRoomsByIsOccupiedIsFalseOrderByNumberAsc();
    }

    public List<Room> getOccupiedRooms() {
        return roomRepo.findRoomsByIsOccupiedIsTrueOrderByNumberAsc();
        //TODO if will be ex
    }

    public Room getRoomById(long id) {
        Optional<Room> room = roomRepo.findById(id);
        return room.orElseThrow();
    }

    //or update
    public void saveRoom(Room room) {
        roomRepo.save(room);
    }
}
