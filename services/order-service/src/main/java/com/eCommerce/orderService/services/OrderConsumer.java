package com.eCommerce.orderService.services;

import com.eCommerce.common.rabbitmq.RabbitMQConstants;
import com.eCommerce.orderService.dtos.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = RabbitMQConstants.ORDER.QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: {}", event);
    }

    @RabbitListener(queues = RabbitMQConstants.PAYMENT.QUEUE)
    public void handlePaymentCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: {}", event);
    }
}
