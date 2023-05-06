package com.example.userservice.kafka.bookingProducer;

import com.example.userservice.DTO.BookingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingService {
    private BookingProducer bookingProducer;

    @Autowired
    public BookingService(BookingProducer bookingProducer) {
        this.bookingProducer = bookingProducer;
    }

    public void sendMessage(BookingDTO message) {
        log.info("[BookingService] send booking to topic");
        bookingProducer.send(message);
    }

}