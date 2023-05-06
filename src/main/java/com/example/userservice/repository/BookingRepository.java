package com.example.userservice.repository;

import com.example.userservice.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, Integer> {
    List<Booking> getBookingsByUserId(String userId);

    List<Booking> getBookingsByCarId(int carId);


    List<Booking> getBookingsByBookingId(int bookingId);

    Booking findByBookingId(String bookingId);

}