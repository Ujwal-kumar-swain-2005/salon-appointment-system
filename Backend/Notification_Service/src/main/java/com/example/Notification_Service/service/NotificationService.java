package com.example.Notification_Service.service;

import com.example.Notification_Service.model.Notification;
import com.example.Notification_Service.payload.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto createNotification(Notification notification) throws Exception;
    List<Notification> getAllNotificationByUserId(Long userId);
    List<Notification> getAllNotificationBySalonId(Long salonId);
    Notification markNotificationAsRead(Long notificationId);
    void markAllNotificationsAsReadBySalonId(Long salonId);
    void markAllNotificationsAsReadByUserId(Long userId);
}