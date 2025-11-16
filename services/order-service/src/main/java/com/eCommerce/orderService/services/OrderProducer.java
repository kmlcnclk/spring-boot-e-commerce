package com.eCommerce.orderService.services;

import com.eCommerce.common.annotations.LogExecutionTime;
import com.eCommerce.common.rabbitmq.RabbitMQConstants;
import com.eCommerce.orderService.dtos.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.eCommerce.common.rabbitmq.RabbitMQService;

@Service
@AllArgsConstructor
public class OrderProducer {

    private final RabbitMQService rabbitMQService;

    @LogExecutionTime
    public void publishOrderCreated(OrderCreatedEvent event) {
        rabbitMQService.sendMessage(RabbitMQConstants.ORDER.class, event);
        rabbitMQService.sendMessage(RabbitMQConstants.PAYMENT.class, event);
        System.out.println("Sent event: " + event);
    }
}

