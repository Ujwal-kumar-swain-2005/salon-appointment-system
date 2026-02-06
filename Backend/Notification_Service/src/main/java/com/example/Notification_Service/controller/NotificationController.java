package com.example.Notification_Service.controller;

import com.example.Notification_Service.client.BookingFeignClient;
import com.example.Notification_Service.mapper.NotificationMapper;
import com.example.Notification_Service.model.Notification;
import com.example.Notification_Service.payload.BookingDto;
import com.example.Notification_Service.payload.NotificationDto;
import com.example.Notification_Service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;

    public NotificationController(NotificationService notificationService, BookingFeignClient bookingFeignClient) {
        this.notificationService = notificationService;
        this.bookingFeignClient = bookingFeignClient;
    }
    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(
            @RequestBody Notification notification) throws Exception {

        NotificationDto dto = notificationService.createNotification(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getByUserId(@PathVariable Long userId) {

        List<Notification> notifications = notificationService.getAllNotificationByUserId(userId);

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
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationDto> markAsRead(@PathVariable Long notificationId) throws Exception {

        Notification notification = notificationService.markNotificationAsRead(notificationId);
        BookingDto bookingDto = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();
        return ResponseEntity.ok(NotificationMapper.toDto(notification, bookingDto));
    }
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllByUserId(
            @PathVariable Long userId) {

        notificationService.markAllNotificationsAsReadByUserId(userId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/salon/{salonId}/read-all")
    public ResponseEntity<Void> markAllBySalonId(
            @PathVariable Long salonId) {

        notificationService.markAllNotificationsAsReadBySalonId(salonId);
        return ResponseEntity.noContent().build();
    }

}
