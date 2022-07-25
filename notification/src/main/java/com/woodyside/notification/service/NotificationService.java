package com.woodyside.notification.service;

import com.woodyside.notification.model.Notification;
import com.woodyside.notification.repository.NotificationRepository;
import com.woodyside.services.notification.payload.request.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.woodyside.notification.util.DateResponseFormatter.getTimestamp;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.insert(
                Notification.builder()
                        .toCustomerId(notificationRequest.getToCustomerId())
                        .toCustomerEmail(notificationRequest.getToCustomerEmail())
                        .sender("Woody Side")
                        .message(notificationRequest.getMessage())
                        .sentAt(getTimestamp())
                        .build()
        );
    }

    @PostConstruct
    public void deleteAllData() {
        notificationRepository.deleteAll();
    }
}
