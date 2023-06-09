package com.example.userservice.controller;

import com.example.userservice.DTO.BookingDTO;
import com.example.userservice.exception.BookingNotFoundException;
import com.example.userservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingsController {
    @Autowired
    BookingService bookingService;

    @Autowired
    public BookingsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/users/{userId}")
    public List<BookingDTO> findBookingsByUserId(@PathVariable("userId") String userId) {
        return bookingService.findBookingsByUserId(userId);
    }

    @PostMapping()
    public ResponseEntity submitNewBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            return ResponseEntity.ok(bookingService.submitNewBooking(bookingDTO));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity returnCar(@PathVariable("bookingId") String bookingId) {
        try {
            return ResponseEntity.ok(bookingService.returnCar(bookingId));
        } catch (BookingNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}