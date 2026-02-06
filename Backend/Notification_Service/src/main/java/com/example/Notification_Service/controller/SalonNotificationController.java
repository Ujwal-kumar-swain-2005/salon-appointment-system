package com.example.Notification_Service.controller;

import com.example.Notification_Service.client.BookingFeignClient;
import com.example.Notification_Service.mapper.NotificationMapper;
import com.example.Notification_Service.model.Notification;
import com.example.Notification_Service.payload.BookingDto;
import com.example.Notification_Service.payload.NotificationDto;
import com.example.Notification_Service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications/salon-owner")
public class SalonNotificationController {
    private static final Logger log = LoggerFactory.getLogger(SalonNotificationController.class);
    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;

    public SalonNotificationController(NotificationService notificationService, BookingFeignClient bookingFeignClient) {
        this.notificationService = notificationService;
        this.bookingFeignClient = bookingFeignClient;
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<NotificationDto>> getBySalonId(@PathVariable Long salonId) {

        List<Notification> notifications = notificationService.getAllNotificationBySalonId(salonId);

        List<NotificationDto> dtos = notifications.stream().map(notification -> {
            BookingDto bookingDto = null;
            try {
                if (notification.getBookingId() != null) {
                    bookingDto = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();
                }
            } catch (Exception ex) {
                log.error("Failed to fetch booking for bookingId={}", notification.getBookingId(), ex);
            }

            return NotificationMapper.toDto(notification, bookingDto);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}
