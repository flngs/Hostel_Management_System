package com.bsuir.kursach.entity;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{1,100}", message = "Letters only!")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{1,100}", message = "Letters only!")
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{1,30}", message = "Letters and numbers only!")
    private String idNumber;

    @NotNull
    @Pattern(regexp = "^[0-9]{1,16}", message = "Numbers only!")
    private String phoneNumber;


    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "room_id")
    private Room room;


    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkoutDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkinDate;

    private boolean isCheckedout;


    @OneToOne(cascade = {})
    @JoinColumn(name = "last_checkedout_room_id")
    private Room lastCheckedoutRoom;

    public Guest() {

    }

    public Guest(String firstName, String lastName, String idNumber, String phoneNumber,
                 Room room, LocalDate checkinDate, LocalDate checkoutDate, Room lastCheckedoutRoom) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.lastCheckedoutRoom = lastCheckedoutRoom;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }


    public boolean getIsCheckedout() {
        return isCheckedout;
    }


    public void setCheckedout(boolean isCheckedout) {
        this.isCheckedout = isCheckedout;
    }


    public Room getLastCheckedoutRoom() {
        return lastCheckedoutRoom;
    }


    public void setLastCheckedoutRoom(Room lastCheckedoutRoom) {
        this.lastCheckedoutRoom = lastCheckedoutRoom;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Guest guest = (Guest) o;
        // field comparison
        return guest.getFirstName().equals(this.firstName) &&
                guest.getLastName().equals(this.lastName) &&
                guest.getId() == this.id &&
                guest.getIdNumber().equals(this.idNumber) &&
                guest.getPhoneNumber().equals(this.phoneNumber);
    }

    public String toString() {
        return "[Guest: id = " + id + ", firstName = " + firstName + ", lastName = " + lastName + ", idNumber = " + idNumber + ", isCheckedout = " + isCheckedout + "]";
    }
}