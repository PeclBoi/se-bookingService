package com.example.userservice.kafka;

import com.example.userservice.DTO.BookingDTO;
import com.example.userservice.model.Car;
import com.example.userservice.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BookingListener {

    @Autowired
    private BookingRepository bookingRepository;

    @KafkaListener(topics = "booking", containerFactory = "kafkaBookingListenerContainerFactory")
    public void newBookingListener(BookingDTO bookingDTO) {
        log.info("Get request from booking topic " + bookingDTO.toString());
    }
}
