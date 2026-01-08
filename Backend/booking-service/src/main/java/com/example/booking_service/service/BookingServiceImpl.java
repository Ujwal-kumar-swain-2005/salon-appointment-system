package com.example.booking_service.service;

import com.example.booking_service.domain.BookingStatus;
import com.example.booking_service.domain.SalonReport;
import com.example.booking_service.modal.Booking;
import com.example.booking_service.payload.BookingRequest;
import com.example.booking_service.payload.OfferingDto;
import com.example.booking_service.payload.SalonDto;
import com.example.booking_service.payload.UserDto;
import com.example.booking_service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest bookingRequest, UserDto userDto, SalonDto salonDto,
                                 Set<OfferingDto> offeringDtos) {
        int totalDuration = offeringDtos.stream().mapToInt(OfferingDto::getDuration).sum();
        LocalDateTime endTime = bookingRequest.getStartTime().plusMinutes(totalDuration);
        try {
            if (isTimeSlotAvailable(salonDto, bookingRequest.getStartTime(), endTime)) {
                int totalPrice = offeringDtos.stream().mapToInt(OfferingDto::getPrice).sum();
                var idList = offeringDtos.stream().map(OfferingDto::getId).collect(Collectors.toSet());
                Booking booking = new Booking();
                booking.setSalonId(salonDto.getId());
                booking.setCustomerId(userDto.getId());
                booking.setStartTime(bookingRequest.getStartTime());
                booking.setEndTime(endTime);
                booking.setOfferingIds(idList);
                booking.setTotalPrice(totalPrice);
                booking.setStatus(BookingStatus.PENDING);
                return bookingRepository.save(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean isTimeSlotAvailable(SalonDto salonDto, LocalDateTime startTime, LocalDateTime endTime)
            throws Exception {
        List<Booking> existingBookings = getBookingsBySalon(salonDto.getId());
        LocalDate date = startTime.toLocalDate();

        LocalDateTime salonOpenTime = salonDto.getOpeningTime().atDate(date);
        LocalDateTime salonCloseTime = salonDto.getClosingTime().atDate(date);
        if (startTime.isBefore(salonOpenTime) || endTime.isAfter(salonCloseTime)) {
            throw new Exception("Booking time is outside salon operating hours.");
        }

        for (Booking booking : existingBookings) {
            if (booking.getStatus() == BookingStatus.CANCELLED) {
                continue;
            }
            LocalDateTime existingStart = booking.getStartTime();
            LocalDateTime existingEnd = booking.getEndTime();
            if (startTime.isBefore(existingEnd) && endTime.isAfter(existingStart)) {
                throw new Exception("Time slot is already booked.");
            }

            if (startTime.equals(existingStart) || endTime.equals(existingEnd)) {
                throw new Exception("Time slot is already booked.");
            }
        }

        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new Exception("Booking not found with id: " + bookingId);
        }
        return booking;
    }

    @Override
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByDate(LocalDateTime date, Long salonId) {
        List<Booking> bookings = bookingRepository.findBySalonId(salonId);
        if (date != null) {
            bookings = bookings.stream().filter(b -> b.getStartTime().toLocalDate().isEqual(date.toLocalDate())
                    || b.getEndTime().toLocalDate().isEqual(date.toLocalDate())).collect(Collectors.toList());
        }
        return bookings;
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        var bookings = getBookingsBySalon(salonId);
        int totalEarnings = bookings.stream().mapToInt(Booking::getTotalPrice).sum();
        int totalBookings = bookings.size();
        List<Booking> cancelledBookings = bookings.stream().filter(b -> b.getStatus() == BookingStatus.CANCELLED)
                .collect(Collectors.toList());

        Double totalRefunds = cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
        report.setTotalEarnings((double) totalEarnings);
        report.setTotalBookings(totalBookings);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalRefunds(totalRefunds);
        return report;

    }

}