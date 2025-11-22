package com.eCommerce.common.errors;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private List<ErrorData> errors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorData {
        private String message;
        private String details;
    }
}