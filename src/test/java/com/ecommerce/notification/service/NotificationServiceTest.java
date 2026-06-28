package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.OrderEvent;
import com.ecommerce.notification.listener.OrderEventListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    private final NotificationService notificationService = new NotificationService();

    @Test
    void sendNotification_buildsExpectedMessage() {
        OrderEvent event = OrderEvent.builder()
                .orderId(500L)
                .customerId(100L)
                .productId(1L)
                .productName("Laptop")
                .quantity(2)
                .totalPrice(new BigDecimal("2400.00"))
                .timestamp(LocalDateTime.now())
                .build();

        String message = notificationService.sendNotification(event);

        assertThat(message)
                .contains("Order #500")
                .contains("customer #100")
                .contains("Laptop")
                .contains("Quantity: 2")
                .contains("2400.00");
    }

    @Test
    void sendNotification_handlesNullProductNameGracefully() {
        OrderEvent event = OrderEvent.builder()
                .orderId(501L)
                .customerId(101L)
                .productId(2L)
                .productName(null)
                .quantity(1)
                .totalPrice(new BigDecimal("10.00"))
                .timestamp(LocalDateTime.now())
                .build();

        String message = notificationService.sendNotification(event);

        assertThat(message).contains("Order #501");
    }

    //listener tests

    @ExtendWith(MockitoExtension.class)
    static class OrderEventListenerTest {

        @Mock
        private NotificationService notificationService;

        @InjectMocks
        private OrderEventListener listener;

        @Test
        void handleOrderEvent_delegatesToService() {
            OrderEvent event = OrderEvent.builder()
                    .orderId(500L)
                    .customerId(100L)
                    .productName("Laptop")
                    .quantity(2)
                    .totalPrice(new BigDecimal("2400.00"))
                    .build();

            listener.handleOrderEvent(event);

            verify(notificationService, times(1)).sendNotification(event);
        }
    }
}
