package com.eCommerce.common.errors.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldNotFoundException extends RuntimeException {

    private String fieldName;

    public FieldNotFoundException(String message) {
        super(message);
    }

    public FieldNotFoundException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }
}
