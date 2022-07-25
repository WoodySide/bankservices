package com.woodyside.services.notification;

import com.woodyside.services.notification.payload.request.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        value = "notification",
        path = "api/v1/notification"
)
public interface NotificationService {

    @PostMapping
    void sendNotification(NotificationRequest notificationRequest);
}
