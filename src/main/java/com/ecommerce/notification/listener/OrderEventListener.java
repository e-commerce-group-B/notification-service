package com.ecommerce.notification.listener;

import com.ecommerce.notification.dto.OrderEvent;
import com.ecommerce.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final NotificationService notificationService;

    public OrderEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue:order-queue}")
    public void handleOrderEvent(OrderEvent event) {
        log.info("Received order event for orderId: {}", event.getOrderId());
        notificationService.sendNotification(event);
    }
}
