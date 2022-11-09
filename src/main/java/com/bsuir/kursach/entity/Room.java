package com.bsuir.kursach.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    public static final double TAX = 0.24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int number;
    private String standard;
    private boolean isOccupied;

    @OneToMany(mappedBy = "room",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    private List<Guest> occupants;

    public Room() {

    }

    public Room(int number, String standard, boolean isOccupied, List<Guest> occupants) {
        super();
        this.number = number;
        this.standard = standard;
        this.isOccupied = isOccupied;
        this.occupants = occupants;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public List<Guest> getOccupants() {
        return occupants;
    }

    public void setOccupants(List<Guest> occupants) {
        this.occupants = occupants;
    }

    public String toString () {
        return standard + " : " + number;
    }

    public double getRate() {
        if(standard.equals("standard")) {
            return 100.0;
        }else if(standard.equals("business")) {
            return 125.0;
        }
        return 150.0;
    }
}