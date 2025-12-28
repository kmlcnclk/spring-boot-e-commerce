package com.eCommerce.orderService.controllers;

import com.eCommerce.common.annotations.ValidateToken;
import com.eCommerce.orderService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/order")
@ValidateToken
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/createOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder() { orderService.createOrder(); }
}
