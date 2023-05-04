package com.example.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;

@Getter
@Setter

public class Bookings {

    private String bookingId;
    private int carId;
    private String userId;

    private Date startDate;

    private Date endDate;

    private Boolean returned;

    public Bookings(int carId, String userId, Date startDate, Date endDate, Boolean returned) {
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = returned;
    }

    public Bookings() {
    }
}
