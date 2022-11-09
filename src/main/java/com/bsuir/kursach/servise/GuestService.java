package com.bsuir.kursach.servise;

import com.bsuir.kursach.entity.Guest;
import com.bsuir.kursach.repository.GuestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    private final GuestRepo guestRepo;

    @Autowired
    public GuestService(GuestRepo guestRepo) {
        this.guestRepo = guestRepo;
    }

    public List<Guest> getActualGuests() {
        return guestRepo.findGuestsByIsCheckedoutIsFalseOrderByLastNameAsc();
    }

    public List<Guest> getCheckedoutGuests() {
        return guestRepo.findGuestsByIsCheckedoutIsTrueOrderByLastNameAsc();
    }

    //or update
    public void saveGuest(Guest guest) {
        guestRepo.save(guest);
    }

    public Guest getGuestById(long id) {
        Optional<Guest> guest = guestRepo.findById(id);
        return guest.orElseThrow();
    }

    //Returns the list of guests which are about to check out tomorrow, today or overstayed
    public List<Guest> getComingCheckouts() {
        LocalDate tomorrow = LocalDate.now().plusDays(2);
        return guestRepo.findCommingCheckouts(tomorrow);
    }

    public int getNightsNumber(Guest guest) {
        LocalDate checkIn = guest.getCheckinDate();
        LocalDate checkOut;

        if(guest.getIsCheckedout()) {
            checkOut = guest.getCheckoutDate();
        }else {
            checkOut = LocalDate.now();
        }

        int nightsNumber = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return nightsNumber;
    }

    public List<Guest> getArchivedGuests() {
        return guestRepo.findGuestsByIsCheckedoutIsTrueOrderByLastNameAsc();
    }
}
