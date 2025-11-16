package com.eCommerce.orderService.controllers;

import lombok.AllArgsConstructor;
import com.eCommerce.orderService.dtos.OrderCreatedEvent;
import com.eCommerce.orderService.services.OrderProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderProducer producer;

    @GetMapping("/send")
    public String send() {
        producer.publishOrderCreated(
                new OrderCreatedEvent("12345", 199.99)
        );
        return "Message sent!";
    }
}
