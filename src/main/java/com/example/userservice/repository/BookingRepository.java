package com.example.userservice.repository;

import com.example.userservice.entity.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Bookings, Integer> {
    List<Bookings> getBookingsByUserId(int userId);

    List<Bookings> getBookingsByCarId(int carId);

    List<Bookings> getBookingsByBookingId(int bookingId);

    Bookings findByBookingId(int bookingId);

}