package com.hex.notification_service.listener;

import com.hex.notification_service.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TicketEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "ticket.notifications.queue")
    public void handleTicketEvent(String message)
    {
        notificationService.sendNotification(message);
    }

}

