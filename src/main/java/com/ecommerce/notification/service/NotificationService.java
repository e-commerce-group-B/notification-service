package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public String sendNotification(OrderEvent event) {
        String message = String.format("Notification: Order #%d for customer #%d has been created. "
                        + "Product: %s, Quantity: %d, Total: %s",
                event.getOrderId(),
                event.getCustomerId(),
                event.getProductName(),
                event.getQuantity(),
                event.getTotalPrice());

        log.info("==================== NOTIFICATION ====================");
        log.info("Timestamp   : {}", LocalDateTime.now());
        log.info("Customer ID : {}", event.getCustomerId());
        log.info("Order ID    : {}", event.getOrderId());
        log.info("Message     : {}", message);
        log.info("======================================================");

        return message;
    }
}
