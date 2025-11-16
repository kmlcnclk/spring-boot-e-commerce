package com.eCommerce.common.rabbitmq.dtos;

import lombok.Data;

@Data
public class RabbitMQModuleConfig {
    private String queue;
    private String exchange;
    private String routingKey;

    public boolean isComplete() {
        return queue != null && exchange != null && routingKey != null;
    }
}