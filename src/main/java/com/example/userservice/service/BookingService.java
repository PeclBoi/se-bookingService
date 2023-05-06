package com.example.userservice.service;


import com.example.userservice.DTO.BookingDTO;
import com.example.userservice.kafka.bookingProducer.BookingProducer;
import com.example.userservice.entity.Bookings;
import com.example.userservice.exception.BookingNotFoundException;
import com.example.userservice.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";


    @Autowired
    BookingRepository bookingsRepository;

    private BookingProducer bookingProducer;

    @Autowired
    public BookingService(BookingProducer bookingProducer) {
        this.bookingProducer = bookingProducer;
    }

    public void sendMessage(BookingDTO message) {
        log.info("[BookingService] send booking to topic");
        bookingProducer.send(message);
    }

    public List<BookingDTO> findBookingsByUserId(int userId) {

        List<BookingDTO> bookingDTOS = new ArrayList<>();
        List<Bookings> bookings = bookingsRepository.getBookingsByUserId(userId);
        bookings.forEach(o -> bookingDTOS.add(convertBookingToBookingDTO(o)));
        return bookingDTOS.stream().sorted((a, b) -> Boolean.compare(a.isReturned(), b.isReturned())).collect(Collectors.toList());
    }

    public BookingDTO submitNewBooking(BookingDTO bookingDTO) throws Exception {

        Bookings booking = new Bookings();

        booking.setBookingId(ObjectId.get().toHexString());
        booking.setCarId(bookingDTO.getCarId());
        booking.setUserId(bookingDTO.getUserId());
        Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingDTO.getPickupDate() + " " + bookingDTO.getPickupHour());
        Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingDTO.getReturnDate() + " " + bookingDTO.getReturnHour());
        booking.setStartDate(pickupDate);
        booking.setEndDate(returnDate);
        booking.setReturned(false);
        Bookings savedBooking = bookingsRepository.save(booking);

        sendMessage(bookingDTO);

        return convertBookingToBookingDTO(savedBooking);
    }

    private BookingDTO convertBookingToBookingDTO(Bookings booking) {

        BookingDTO bookingDTO = new BookingDTO();

        //bookingsDTO.setBookingId(booking.getBookingId());
        bookingDTO.setCarId(booking.getCarId());
        bookingDTO.setUserId(booking.getUserId());
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_FORMAT);
        String startDate = dateFormatter.format(booking.getStartDate());
        String startTime = timeFormatter.format(booking.getStartDate());
        String endDate = dateFormatter.format(booking.getEndDate());
        String endTime = timeFormatter.format(booking.getEndDate());
        bookingDTO.setPickupDate(startDate);
        bookingDTO.setPickupHour(startTime);
        bookingDTO.setReturnDate(endDate);
        bookingDTO.setReturnHour(endTime);
        bookingDTO.setReturned(booking.getReturned());

        return bookingDTO;
    }

    public BookingDTO returnCar(int bookingId) throws BookingNotFoundException {

        Bookings booking;
        try {
            booking = bookingsRepository.findByBookingId(bookingId);
        } catch (Exception exception) {
            throw exception;
        }

        Bookings savedBooking = bookingsRepository.save(booking);
        booking.setReturned(true);
        return convertBookingToBookingDTO(savedBooking);
    }

}
