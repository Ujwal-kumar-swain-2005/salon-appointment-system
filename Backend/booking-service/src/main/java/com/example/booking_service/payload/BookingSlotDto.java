package com.example.booking_service.payload;

import java.time.LocalDateTime;

public class BookingSlotDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public BookingSlotDto() {
    }

    public BookingSlotDto(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}