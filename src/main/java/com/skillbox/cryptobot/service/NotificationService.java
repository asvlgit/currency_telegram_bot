package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.model.Notification;
import com.skillbox.cryptobot.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void saveNotification(UUID subscriberId, LocalDateTime dateNotification) {
        Notification notification = new Notification();
        notification.setIdSubscriber(subscriberId);
        notification.setLastSend(dateNotification);
        notificationRepository.save(notification);
    }

}
