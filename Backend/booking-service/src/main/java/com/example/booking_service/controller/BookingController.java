package com.example.booking_service.controller;

import com.example.booking_service.domain.BookingStatus;
import com.example.booking_service.modal.Booking;
import com.example.booking_service.payload.*;
import com.example.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long salonId,
                                                 @RequestBody BookingRequest bookingRequest) {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        SalonDto salonDto = new SalonDto();
        salonDto.setId(salonId);
        salonDto.setOpeningTime(LocalTime.of(8, 0));
        salonDto.setClosingTime(LocalTime.of(20, 0));

        Set<OfferingDto> offeringDtos = new HashSet<>();
        OfferingDto offering = new OfferingDto();
        offering.setId(1L);
        offering.setPrice(100);
        offering.setDuration(60);
        offering.setName("Haircut");
        offeringDtos.add(offering);

        Booking booking = bookingService.createBooking(bookingRequest, userDto, salonDto, offeringDtos);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.badRequest().build();
        }

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