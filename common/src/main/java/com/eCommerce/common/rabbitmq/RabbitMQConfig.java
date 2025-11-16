package com.eCommerce.common.rabbitmq;

import com.eCommerce.common.rabbitmq.dtos.RabbitMQModuleConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    /**
     * Dynamically creates all queues, exchanges, and bindings from RabbitMQConstants
     * Uses the centralized loadModuleConfigs() method to avoid code duplication
     */
    @Bean
    public Declarables rabbitMQDeclarables() {
        var declarables = new ArrayList<Declarable>();

        // Use the centralized configuration loader
        Map<String, RabbitMQModuleConfig> modules = RabbitMQConfigLoader.loadModuleConfigs();

        // Create declarables for each module
        modules.forEach((moduleName, config) -> {
            if (config.isComplete()) {
                log.info("Creating RabbitMQ infrastructure for module: {}", moduleName);
                log.info("  Queue: {}", config.getQueue());
                log.info("  Exchange: {}", config.getExchange());
                log.info("  Routing Key: {}", config.getRoutingKey());

                // Create Queue
                Queue queue = QueueBuilder.durable(config.getQueue()).build();

                // Create Exchange
                TopicExchange exchange = new TopicExchange(config.getExchange(), true, false);

                // Create Binding
                Binding binding = BindingBuilder
                        .bind(queue)
                        .to(exchange)
                        .with(config.getRoutingKey());

                declarables.add(queue);
                declarables.add(exchange);
                declarables.add(binding);
            } else {
                log.warn("Incomplete configuration for module: {} - skipping", moduleName);
            }
        });

        log.info("Created {} RabbitMQ declarables", declarables.size());
        return new Declarables(declarables);
    }
}