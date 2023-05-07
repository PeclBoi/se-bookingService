package com.example.userservice.service;


import com.example.userservice.DTO.CarDTO;
import com.example.userservice.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CarService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

//    private CarProducer carProducer;

//    @Autowired
//    public CarService(CarProducer carProducer) {
//        this.carProducer = carProducer;
//    }

    @Transactional(readOnly = true)
    public List<CarDTO> findCars(String pickupdate, String pickuphour, String returndate, String returnhour, String currency) throws ParseException {
        List<CarDTO> carsDTOS = new ArrayList<>();
        return carsDTOS;
    }

    @Transactional(readOnly = true)
    public List<CarDTO> getAllCars() {
        List<CarDTO> carsDTOS = new ArrayList<>();
        return carsDTOS;
    }

//    public void sendMessage(CarDTO message) {
//        log.info("[CarService] send car to topic");
//        carProducer.send(message);
//    }

    private boolean isNotInRange(Date date, Date startDate, Date endDate) {
        return date.before(startDate) || date.after(endDate);
    }

    //todo currency service call
    /*private BigDecimal convertCurrency(BigDecimal amount, String from, String to) {
        mypackage.WebService1Soap service = new WebService1().getPort(WebService1Soap.class);
        return service.convertCurrency(amount, from, to);
        //return BigDecimal.valueOf(50);
    }*/

    private CarDTO convertCarToCarDTO(Car car, String currency) {
        CarDTO carsDTO = new CarDTO();
        carsDTO.setCarId(car.getId());
        carsDTO.setMake(car.getMake());
        carsDTO.setModel(car.getModel());
        carsDTO.setYear(car.getYear());
        carsDTO.setCurrency(currency);
        //todo currency??
        carsDTO.setDailyRate(BigDecimal.valueOf(car.getDailyRate()));
        return carsDTO;
    }

}
