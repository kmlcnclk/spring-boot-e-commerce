package com.eCommerce.userService.services;

import com.eCommerce.common.domain.User;
import com.eCommerce.common.repositories.UserRepository;
import com.eCommerce.userService.dtos.CreateUserDto;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repo;

    @CacheEvict(value = "users", allEntries = true)
    public User saveUser(CreateUserDto user) {
        return repo.save(CreateUserDto.toEntity(user));
    }

    @Cacheable(value = "users")
    public List<User> getAllUsers() {
        System.out.println("123");
        return repo.findAll();
    }

    public Optional<User> getByEmail(String email) {
        return repo.findByEmail(email);
    }
}

