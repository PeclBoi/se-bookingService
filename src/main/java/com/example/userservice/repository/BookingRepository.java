package com.example.userservice.repository;

import com.example.userservice.entity.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends MongoRepository<Bookings, Integer> {
}