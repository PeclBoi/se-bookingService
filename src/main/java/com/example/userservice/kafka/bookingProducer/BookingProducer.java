package com.example.userservice.kafka.bookingProducer;

import com.example.userservice.DTO.BookingDTO;
import com.example.userservice.DTO.ReturningDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Slf4j
@NoArgsConstructor
@Component
public class BookingProducer {
    final String bookingTopic = "booking";
    final String returningTopic = "returning";

    private KafkaTemplate<String, Serializable> kafkaTemplate;

    @Autowired
    public BookingProducer(KafkaTemplate<String, Serializable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBooking(BookingDTO message) {
        CompletableFuture<SendResult<String, Serializable>> future = kafkaTemplate.send(bookingTopic, message);
        future.whenComplete(new BiConsumer<SendResult<String, Serializable>, Throwable>() {
            @Override
            public void accept(SendResult<String, Serializable> stringSerializableSendResult, Throwable throwable) {
                log.info("Message sent successfully with offset = {}", stringSerializableSendResult.getRecordMetadata().offset());
            }
        });
    }

    public void sendReturning(ReturningDTO message) {
        CompletableFuture<SendResult<String, Serializable>> future = kafkaTemplate.send(returningTopic, message);
        future.whenComplete(new BiConsumer<SendResult<String, Serializable>, Throwable>() {
            @Override
            public void accept(SendResult<String, Serializable> stringSerializableSendResult, Throwable throwable) {
                log.info("Message sent successfully with offset = {}", stringSerializableSendResult.getRecordMetadata().offset());
            }
        });
    }

}