package com.eCommerce.userService.controllers;

import com.eCommerce.common.annotations.ValidateToken;
import com.eCommerce.common.domain.User;
import com.eCommerce.userService.dtos.CreateUserDto;
import com.eCommerce.userService.services.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@ValidateToken
public class UserController {

    private final UserService service;

    @PostMapping
    @RateLimiter(name = "userRateLimiter")
    public User createUser(@RequestBody @Valid CreateUserDto user) {
        return service.saveUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getAllUsers();
    }
}
