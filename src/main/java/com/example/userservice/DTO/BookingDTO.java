package com.example.userservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class BookingDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3965748134914559751L;

    private String bookingId;

    private String userId;

    private int carId;

    private String pickupDate;

    private String pickupHour;

    private String returnDate;

    private String returnHour;

    private boolean returned;

}
