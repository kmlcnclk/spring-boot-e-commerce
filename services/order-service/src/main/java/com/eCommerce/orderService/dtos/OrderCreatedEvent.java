package com.eCommerce.orderService.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private double totalPrice;

    @Override
    public String toString() {
        return "OrderCreatedEvent{orderId='" + orderId + "', totalPrice=" + totalPrice + "}";
    }
}

