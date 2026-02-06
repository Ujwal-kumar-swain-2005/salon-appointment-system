package com.example.Notification_Service.client;

import com.example.Notification_Service.payload.BookingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "BOOKING-SERVICE", url = "http://localhost:8085")
public interface BookingFeignClient {

    @GetMapping("/api/booking/{bookingId}")
    ResponseEntity<BookingDto> getBookingsById(
            @PathVariable("bookingId") Long bookingId
    );
}
