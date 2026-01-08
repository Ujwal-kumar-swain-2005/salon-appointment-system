package com.example.booking_service.payload;

import java.time.LocalDateTime;
import java.util.Set;

public class BookingRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<Long> offeringIds;

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

    public Set<Long> getOfferingIds() {
        return offeringIds;
    }

    public void setOfferingIds(Set<Long> offeringIds) {
        this.offeringIds = offeringIds;
    }
}