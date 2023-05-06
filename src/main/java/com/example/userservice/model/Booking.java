package com.example.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@AllArgsConstructor
public class Booking {
    @Id
    private String bookingId;

    private String userId;

    private int carId;

    private Date pickupDate;

    //private String pickupHour;

    private Date returnDate;

   // private String returnHour;

    private boolean returned;

    public Booking() {

    }

}
