package com.eCommerce.common.domain;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String name;

    @Email
    private String email;
}

