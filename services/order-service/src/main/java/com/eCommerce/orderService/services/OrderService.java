package com.eCommerce.orderService.services;

import com.eCommerce.common.rabbitmq.RabbitMQConstants;
import com.eCommerce.orderService.dtos.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderProducer producer;

    public void createOrder() {
        // assume create order logic in here

        OrderCreatedEvent event = new OrderCreatedEvent("12345", 199.99);

        // send payment event
        producer.publishOrderCreated(RabbitMQConstants.PAYMENT.class, event);

        // send order event
        producer.publishOrderCreated(RabbitMQConstants.ORDER.class, event);

        // send notification event
        producer.publishOrderCreated(RabbitMQConstants.NOTIFICATION.class, event);
    }
}
