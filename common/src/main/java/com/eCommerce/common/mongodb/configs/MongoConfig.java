package com.eCommerce.common.mongodb.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;

@Configuration
public class MongoConfig {

    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    @PostConstruct
    public void removeClassField() {
        // This will stop Spring Data from writing _class in MongoDB
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}
