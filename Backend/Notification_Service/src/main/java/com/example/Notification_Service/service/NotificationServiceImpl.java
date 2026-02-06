package com.example.Notification_Service.service;

import com.example.Notification_Service.client.BookingFeignClient;
import com.example.Notification_Service.mapper.NotificationMapper;
import com.example.Notification_Service.model.Notification;
import com.example.Notification_Service.payload.NotificationDto;
import com.example.Notification_Service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;

    public NotificationServiceImpl(NotificationRepository notificationRepository, BookingFeignClient bookingFeignClient) {
        this.notificationRepository = notificationRepository;
        this.bookingFeignClient = bookingFeignClient;
    }

    @Override
    public NotificationDto createNotification(Notification notification) throws Exception {
        var newNotifi = notificationRepository.save(notification);
        var bookingDto = bookingFeignClient.getBookingsById(newNotifi.getBookingId()).getBody();

        return NotificationMapper.toDto(newNotifi, bookingDto);

    }

    @Override
    public List<Notification> getAllNotificationByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        return notificationRepository.save(n);
    }

    @Override
    public void markAllNotificationsAsReadBySalonId(Long salonId) {
        List<Notification> list = notificationRepository.findBySalonId(salonId);

        if (list.isEmpty()) {
            return;
        }

        list.forEach(n -> n.setRead(true));

        notificationRepository.saveAll(list);
    }

    @Override
    public void markAllNotificationsAsReadByUserId(Long userId) {
        List<Notification> list = notificationRepository.findByUserId(userId);

        if (list.isEmpty()) {
            return;
        }

        list.forEach(n -> n.setRead(true));

        notificationRepository.saveAll(list);
    }

}