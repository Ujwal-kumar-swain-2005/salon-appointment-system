package com.example.booking_service.service;

import com.example.booking_service.domain.BookingStatus;
import com.example.booking_service.domain.SalonReport;
import com.example.booking_service.modal.Booking;
import com.example.booking_service.payload.BookingRequest;
import com.example.booking_service.payload.OfferingDto;
import com.example.booking_service.payload.SalonDto;
import com.example.booking_service.payload.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BookingService {
    Booking createBooking(BookingRequest bookingRequest, UserDto userDto, SalonDto salonDto,
                          Set<OfferingDto> offeringDtos);

    List<Booking> getBookingsByCustomer(Long customerId);

    List<Booking> getBookingsBySalon(Long salonId);

    Booking getBookingById(Long bookingId) throws Exception;

    Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception;

    List<Booking> getBookingByDate(LocalDateTime date, Long salonId);

    SalonReport getSalonReport(Long salonId);
}