package com.example.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class Bookings {

    private int carId;
    private int userId;
    private int bookingId;

    private Date startDate;

    private Date endDate;

    private Boolean returned;

    public Bookings(int carId, int userId, Date startDate, Date endDate, Boolean returned) {
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = returned;
    }

    public Bookings() {
    }
}
