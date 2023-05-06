package com.example.userservice.service;


import com.example.userservice.DTO.BookingDTO;
import com.example.userservice.DTO.ReturningDTO;
import com.example.userservice.kafka.bookingProducer.BookingProducer;
import com.example.userservice.exception.BookingNotFoundException;
import com.example.userservice.model.Booking;
import com.example.userservice.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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
        bookingProducer.sendBooking(message);
    }

    public List<BookingDTO> findBookingsByUserId(String userId) {

        List<BookingDTO> bookingDTOS = new ArrayList<>();
        List<Booking> bookings = bookingsRepository.getBookingsByUserId(userId);
        bookings.forEach(o -> bookingDTOS.add(convertBookingToBookingDTO(o)));
        return bookingDTOS;
    }

    public BookingDTO submitNewBooking(BookingDTO bookingDTO) throws Exception {

        Booking booking = new Booking();

        booking.setBookingId(ObjectId.get().toHexString());
        booking.setCarId(bookingDTO.getCarId());
        booking.setUserId(bookingDTO.getUserId());
        Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingDTO.getPickupDate() + " " + bookingDTO.getPickupHour());
        Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingDTO.getReturnDate() + " " + bookingDTO.getReturnHour());
        booking.setPickupDate(pickupDate);
        booking.setReturnDate(returnDate);
        booking.setReturned(false);

        Booking savedBooking = bookingsRepository.save(booking);

        bookingDTO = convertBookingToBookingDTO(savedBooking);

        sendMessage(bookingDTO);

        return bookingDTO;
    }


    private BookingDTO convertBookingToBookingDTO(Booking booking) {

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setCarId(booking.getCarId());
        bookingDTO.setUserId(booking.getUserId());
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_FORMAT);
        String startDate = dateFormatter.format(booking.getPickupDate());
        String startTime = timeFormatter.format(booking.getPickupDate());
        String endDate = dateFormatter.format(booking.getReturnDate());
        String endTime = timeFormatter.format(booking.getReturnDate());
        bookingDTO.setPickupDate(startDate);
        bookingDTO.setPickupHour(startTime);
        bookingDTO.setReturnDate(endDate);
        bookingDTO.setReturnHour(endTime);

        return bookingDTO;
    }

    public BookingDTO returnCar(String bookingId) throws BookingNotFoundException {

        Booking booking;
        try {
            booking = bookingsRepository.findByBookingId(bookingId);
        } catch (Exception exception) {
            throw exception;
        }

        booking.setReturned(true);
        Booking savedBooking = bookingsRepository.save(booking);
        ReturningDTO returningDTO = new ReturningDTO();
        returningDTO.setBookingId(booking.getBookingId());
        returningDTO.setCarId(booking.getCarId());
        bookingProducer.sendReturning(returningDTO);

        return convertBookingToBookingDTO(savedBooking);
    }

}
