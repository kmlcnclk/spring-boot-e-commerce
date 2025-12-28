package com.eCommerce.authService;

import com.eCommerce.authService.dtos.AuthResponse;
import com.eCommerce.authService.dtos.RegisterRequest;
import com.eCommerce.authService.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Test
    void shouldCreateToken() throws Exception {
        RegisterRequest request = new RegisterRequest("test1231","P@ssw0rd123","test1231@gmail.com","123","123");

        AuthResponse res = authService.register(request);

        System.out.println(res);
    }

}