package com.example.booking_service.controller;

import com.example.booking_service.domain.BookingStatus;
import com.example.booking_service.modal.Booking;
import com.example.booking_service.payload.*;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.service.client.OfferingFeignClient;
import com.example.booking_service.service.client.PaymentFeignClient;
import com.example.booking_service.service.client.SalonFeignClient;
import com.example.booking_service.service.client.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/booking")

public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private SalonFeignClient salonFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private PaymentFeignClient paymentFeignClient;
    @Autowired
    private OfferingFeignClient offeringFeignClient;

    @PostMapping
    public BookingDto createBooking(@RequestParam Long salonId,

                                                 @RequestBody BookingRequest bookingRequest,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        var userDto = userFeignClient.getUserInfo(token).getBody();
        var salonDto = salonFeignClient.getSalonById(salonId).getBody();
        var offeringDtos = offeringFeignClient.getServiceOfferingByIds(
                bookingRequest.getOfferingIds()).getBody();
        if(offeringDtos.isEmpty()) {
            throw new Exception("Service not found...");
        }

        var booking = bookingService.createBooking(bookingRequest, userDto, salonDto, offeringDtos);


        BookingDto bookingDto = BookingMapper.toDto(booking);

    return bookingDto;

    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDto>> getBookingsByCustomer() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        List<Booking> bookings = bookingService.getBookingsByCustomer(userDto.getId());
        return ResponseEntity.ok(getBookings(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDto>> getBookingsBySalon() {
        List<Booking> bookings = bookingService.getBookingsBySalon(1L);
        return ResponseEntity.ok(getBookings(bookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingsById(@PathVariable Long bookingId) throws Exception {
        return ResponseEntity.ok(BookingMapper.toDto(bookingService.getBookingById(bookingId)));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDto> updateBookingStatus(@PathVariable Long bookingId, @RequestParam String status)
            throws Exception {
        return ResponseEntity
                .ok(BookingMapper.toDto(bookingService.updateBookingStatus(bookingId, BookingStatus.valueOf(status))));
    }

    @GetMapping("/slots/salon/{salonId}")
    public ResponseEntity<List<BookingSlotDto>> getBookingsByDate(
            @PathVariable Long salonId,
            @RequestParam LocalDate date) {

        var bookings = bookingService.getBookingByDate(date.atStartOfDay(), salonId);
        var bookingSlots = bookings.stream().map(b -> {
            var slot = new BookingSlotDto();
            slot.setStartTime(b.getStartTime());
            slot.setEndTime(b.getEndTime());
            return slot;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(bookingSlots);
    }


    private Set<BookingDto> getBookings(List<Booking> bookings) {
        return bookings.stream().map(b -> {
            return BookingMapper.toDto(b);
        }).collect(Collectors.toSet());
    }

    @GetMapping("/report")
    public ResponseEntity<?> getSalonReport() {
        var report = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(report);
    }
}