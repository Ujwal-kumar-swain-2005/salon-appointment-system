package com.example.Notification_Service.mapper;

import com.example.Notification_Service.model.Notification;
import com.example.Notification_Service.payload.BookingDto;
import com.example.Notification_Service.payload.NotificationDto;

public class NotificationMapper {

    public static NotificationDto toDto(Notification notification, BookingDto bookingDto) {
        if (notification == null) {
            return null;
        }

        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setType(notification.getType());
        dto.setDescription(notification.getDescription());
        dto.setRead(notification.getRead());
        dto.setUserId(notification.getUserId());
        dto.setSalonId(notification.getSalonId());
        dto.setBookingId(bookingDto.getId());
        dto.setCreatedAt(notification.getCreatedAt());

        return dto;
    }
}