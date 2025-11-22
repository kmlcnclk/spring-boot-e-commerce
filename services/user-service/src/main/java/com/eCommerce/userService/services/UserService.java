package com.eCommerce.userService.services;

import com.eCommerce.common.domain.User;
import com.eCommerce.common.repositories.UserRepository;
import com.eCommerce.userService.dtos.CreateUserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repo;

    public User saveUser(CreateUserDto user) {
        return repo.save(CreateUserDto.toEntity(user));
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getByEmail(String email) {
        return repo.findByEmail(email);
    }
}

