package com.eCommerce.common.rabbitmq;

import com.eCommerce.common.rabbitmq.dtos.RabbitMQModuleConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@Slf4j
@Service
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final Map<String, RabbitMQModuleConfig> moduleConfigs;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.moduleConfigs = RabbitMQConfigLoader.loadModuleConfigs();
    }

    /**
     * Send message to a specific module's queue
     * Usage: rabbitMQService.sendMessage(RabbitMQConstants.ORDER.class, orderMessage);
     * @param moduleClass The module class (e.g., RabbitMQConstants.ORDER.class)
     * @param message The message object to send
     */
    public void sendMessage(Class<?> moduleClass, Object message) {
        String moduleName = moduleClass.getSimpleName();
        RabbitMQModuleConfig config = moduleConfigs.get(moduleName);

        if (config == null || !config.isComplete()) {
            throw new IllegalArgumentException("Module not found or incomplete: " + moduleName);
        }

        log.debug("Sending message to module {}: exchange={}, routingKey={}",
                moduleName, config.getExchange(), config.getRoutingKey());

        rabbitTemplate.convertAndSend(
                config.getExchange(),
                config.getRoutingKey(),
                message
        );

        log.info("Message sent successfully to module: {}", moduleName);
    }

    /**
     * Send message using direct exchange and routing key
     * @param exchange The exchange name
     * @param routingKey The routing key
     * @param message The message object to send
     */
    public void sendMessage(String exchange, String routingKey, Object message) {
        log.debug("Sending message to exchange={}, routingKey={}", exchange, routingKey);

        rabbitTemplate.convertAndSend(exchange, routingKey, message);

        log.info("Message sent successfully to exchange: {}, routingKey: {}", exchange, routingKey);
    }

    /**
     * Get queue name for a module
     * Usage: rabbitMQService.getQueueName(RabbitMQConstants.ORDER.class);
     * @param moduleClass The module class
     * @return The queue name or null if not found
     */
    public String getQueueName(Class<?> moduleClass) {
        String moduleName = moduleClass.getSimpleName();
        RabbitMQModuleConfig config = moduleConfigs.get(moduleName);
        return config != null ? config.getQueue() : null;
    }

    /**
     * Get exchange name for a module
     * Usage: rabbitMQService.getExchangeName(RabbitMQConstants.ORDER.class);
     * @param moduleClass The module class
     * @return The exchange name or null if not found
     */
    public String getExchangeName(Class<?> moduleClass) {
        String moduleName = moduleClass.getSimpleName();
        RabbitMQModuleConfig config = moduleConfigs.get(moduleName);
        return config != null ? config.getExchange() : null;
    }

    /**
     * Get routing key for a module
     * Usage: rabbitMQService.getRoutingKey(RabbitMQConstants.ORDER.class);
     * @param moduleClass The module class
     * @return The routing key or null if not found
     */
    public String getRoutingKey(Class<?> moduleClass) {
        String moduleName = moduleClass.getSimpleName();
        RabbitMQModuleConfig config = moduleConfigs.get(moduleName);
        return config != null ? config.getRoutingKey() : null;
    }

    /**
     * Check if a module configuration exists and is complete
     * Usage: rabbitMQService.isModuleConfigured(RabbitMQConstants.ORDER.class);
     * @param moduleClass The module class to check
     * @return true if the module exists and has all required configurations
     */
    public boolean isModuleConfigured(Class<?> moduleClass) {
        String moduleName = moduleClass.getSimpleName();
        RabbitMQModuleConfig config = moduleConfigs.get(moduleName);
        return config != null && config.isComplete();
    }

    /**
     * Get configuration for a specific module
     * Usage: rabbitMQService.getModuleConfig(RabbitMQConstants.ORDER.class);
     * @param moduleClass The module class
     * @return The module configuration or null if not found
     */
    public RabbitMQModuleConfig getModuleConfig(Class<?> moduleClass) {
        String moduleName = moduleClass.getSimpleName();
        return moduleConfigs.get(moduleName);
    }

    /**
     * Get all configured module names
     * @return Set of all configured module names
     */
    public java.util.Set<String> getConfiguredModules() {
        return moduleConfigs.keySet();
    }
}