package com.example.userservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class CarDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7952434632086438275L;

    private int carId;

    private String make;

    private String model;

    private String year;

    private String currency;

    private BigDecimal dailyRate;

    @Override
    public String toString() {
        return "CarDTO{" +
                "carId=" + carId +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", currency='" + currency + '\'' +
                ", dailyRate=" + dailyRate +
                '}';
    }
}
