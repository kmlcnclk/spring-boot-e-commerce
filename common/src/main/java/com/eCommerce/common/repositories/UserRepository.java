package com.eCommerce.common.repositories;

import com.eCommerce.common.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}

