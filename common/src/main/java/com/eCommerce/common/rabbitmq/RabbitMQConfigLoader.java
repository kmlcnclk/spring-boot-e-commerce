package com.eCommerce.common.rabbitmq;

import com.eCommerce.common.rabbitmq.dtos.RabbitMQModuleConfig;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Component
@Slf4j
public class RabbitMQConfigLoader {

    /**
     * Centralized method to load all module configurations using reflection
     * This method is used by both RabbitMQConfig and RabbitMQService
     */
    public static Map<String, RabbitMQModuleConfig> loadModuleConfigs() {
        Map<String, RabbitMQModuleConfig> configs = new HashMap<>();

        try {
            Class<?>[] declaredClasses = RabbitMQConstants.class.getDeclaredClasses();

            for (Class<?> moduleClass : declaredClasses) {
                if (java.lang.reflect.Modifier.isStatic(moduleClass.getModifiers())) {
                    String moduleName = moduleClass.getSimpleName();
                    RabbitMQModuleConfig config = extractConfigFromClass(moduleClass);
                    configs.put(moduleName, config);
                }
            }

            log.info("Loaded {} module configurations from RabbitMQConstants", configs.size());
            configs.forEach((name, config) -> {
                log.debug("Module {}: queue={}, exchange={}, routingKey={}",
                        name, config.getQueue(), config.getExchange(), config.getRoutingKey());
            });

        } catch (IllegalAccessException e) {
            log.error("Error loading RabbitMQ module configurations", e);
            throw new RuntimeException("Failed to load RabbitMQ configurations", e);
        }

        return configs;
    }

    /**
     * Extract configuration from a nested static class
     */
    private static RabbitMQModuleConfig extractConfigFromClass(Class<?> moduleClass) throws IllegalAccessException {
        RabbitMQModuleConfig config = new RabbitMQModuleConfig();
        Field[] fields = moduleClass.getDeclaredFields();

        for (Field field : fields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(String.class)) {

                String fieldName = field.getName();
                String value = (String) field.get(null);

                switch (fieldName) {
                    case "QUEUE":
                        config.setQueue(value);
                        break;
                    case "EXCHANGE":
                        config.setExchange(value);
                        break;
                    case "ROUTING_KEY":
                        config.setRoutingKey(value);
                        break;
                }
            }
        }
        return config;
    }
}