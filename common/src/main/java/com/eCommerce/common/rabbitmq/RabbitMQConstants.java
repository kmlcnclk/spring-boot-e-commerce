package com.eCommerce.common.rabbitmq;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class RabbitMQConstants {

    // -------- ORDER MODULE --------
    public static class ORDER {
        public static final String QUEUE = "order.created.queue";
        public static final String EXCHANGE = "order.exchange";
        public static final String ROUTING_KEY = "order.created";
    }

    // -------- PAYMENT MODULE --------
    public static class PAYMENT {
        public static final String QUEUE = "payment.processed.queue";
        public static final String EXCHANGE = "payment.exchange";
        public static final String ROUTING_KEY = "payment.processed";
    }

    // -------- NOTIFICATION MODULE --------
    public static class NOTIFICATION {
        public static final String QUEUE = "notification.processed.queue";
        public static final String EXCHANGE = "notification.exchange";
        public static final String ROUTING_KEY = "notification.processed";
    }
}