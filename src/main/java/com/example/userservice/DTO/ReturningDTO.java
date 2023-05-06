package com.example.userservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class ReturningDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4178042577951489044L;

    private int carId;

}
