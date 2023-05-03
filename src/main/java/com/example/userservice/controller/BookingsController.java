package com.example.userservice.controller;

import com.example.userservice.DTO.BookingsDTO;
import com.example.userservice.entity.Bookings;
import com.example.userservice.repository.BookingRepository;
import com.example.userservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    @Autowired
    BookingService bookingService;

    @GetMapping()
    public List<BookingsDTO> list() {
        return bookingService.findBookingsByUserId(1);
    }

    @PostMapping()
    public ResponseEntity submitNewBooking(@RequestBody BookingsDTO bookingsDTO) {
        try {
            return ResponseEntity.ok(bookingService.submitNewBooking(bookingsDTO));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}