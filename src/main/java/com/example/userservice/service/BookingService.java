package com.example.userservice.service;


import com.example.userservice.DTO.BookingsDTO;
import com.example.userservice.entity.Bookings;
import com.example.userservice.exception.BookingNotFoundException;
import com.example.userservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";


    @Autowired
    BookingRepository bookingsRepository;

    public List<BookingsDTO> findBookingsByUserId(int userId) {
        List<BookingsDTO> bookingsDTOS = new ArrayList<>();
            List<Bookings> bookings = bookingsRepository.findAll();
            bookings.forEach(o -> bookingsDTOS.add(convertBookingToBookingDTO(o)));
        return bookingsDTOS.stream().sorted((a, b) -> Boolean.compare(a.isReturned(), b.isReturned())).collect(Collectors.toList());
    }

    public BookingsDTO submitNewBooking(BookingsDTO bookingsDTO) throws ParseException {

        Bookings booking = new Bookings();

        booking.setCarId(bookingsDTO.getCarId());
        booking.setUserId(bookingsDTO.getUserId());
        Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingsDTO.getPickupDate() + " " + bookingsDTO.getPickupHour());
        Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingsDTO.getReturnDate() + " " + bookingsDTO.getReturnHour());
        booking.setStartDate(pickupDate);
        booking.setEndDate(returnDate);
        booking.setReturned(false);
        Bookings savedBooking = bookingsRepository.save(booking);

        return convertBookingToBookingDTO(savedBooking);
    }

    private BookingsDTO convertBookingToBookingDTO(Bookings booking) {

        BookingsDTO bookingsDTO = new BookingsDTO();

        bookingsDTO.setBookingId(booking.getBookingId());
        bookingsDTO.setCarId(booking.getCarId());
        bookingsDTO.setUserId(booking.getUserId());
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_FORMAT);
        String startDate = dateFormatter.format(booking.getStartDate());
        String startTime = timeFormatter.format(booking.getStartDate());
        String endDate = dateFormatter.format(booking.getEndDate());
        String endTime = timeFormatter.format(booking.getEndDate());
        bookingsDTO.setPickupDate(startDate);
        bookingsDTO.setPickupHour(startTime);
        bookingsDTO.setReturnDate(endDate);
        bookingsDTO.setReturnHour(endTime);
        bookingsDTO.setReturned(booking.getReturned());
        return bookingsDTO;
    }

    public BookingsDTO returnCar(int bookingId) throws BookingNotFoundException {
        Optional<Bookings> booking = bookingsRepository.findById(bookingId);
        if (booking.isPresent()) {
            booking.get().setReturned(true);
            Bookings savedBooking = bookingsRepository.save(booking.get());
            return convertBookingToBookingDTO(savedBooking);
        }
        throw new BookingNotFoundException("Booking with Id " + bookingId + " not found");
    }

}
