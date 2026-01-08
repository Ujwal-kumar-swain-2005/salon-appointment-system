package com.example.booking_service.payload;

import com.example.booking_service.modal.Booking;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setSalonId(booking.getSalonId());
        dto.setCustomerId(booking.getCustomerId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setOfferingIds(booking.getOfferingIds());
        dto.setStatus(booking.getStatus());
        return dto;
    }
}