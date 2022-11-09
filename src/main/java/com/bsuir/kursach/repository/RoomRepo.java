package com.bsuir.kursach.repository;

import com.bsuir.kursach.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends CrudRepository<Room, Long> {
    List<Room> findRoomsByIsOccupiedIsFalseOrderByNumberAsc();
    List<Room> findRoomsByIsOccupiedIsTrueOrderByNumberAsc();
}
