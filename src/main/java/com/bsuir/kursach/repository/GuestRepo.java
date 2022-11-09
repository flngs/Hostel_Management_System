package com.bsuir.kursach.repository;

import com.bsuir.kursach.entity.Guest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GuestRepo extends CrudRepository<Guest, Long> {
    List<Guest> findGuestsByIsCheckedoutIsFalseOrderByLastNameAsc();
    List<Guest> findGuestsByIsCheckedoutIsTrueOrderByLastNameAsc();

    @Query(value = "select g from Guest g where g.checkoutDate <= :tomorrow and g.isCheckedout = false")
    List<Guest> findCommingCheckouts(@Param("tomorrow") LocalDate tomorrow);

}
