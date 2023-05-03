package com.example.userservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingsDTO {

    private int bookingId;

    private int userId;

    private int carId;

    private String pickupDate;

    private String pickupHour;

    private String returnDate;

    private String returnHour;

    private boolean returned;

}
