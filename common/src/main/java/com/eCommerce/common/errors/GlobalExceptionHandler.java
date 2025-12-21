package com.eCommerce.common.errors;

import com.eCommerce.common.errors.exceptions.FieldNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    /* ===================== 400 BAD REQUEST ===================== */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        List<ErrorResponse.ErrorData> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.ErrorData(
                        error.getDefaultMessage(),
                        "field=" + error.getField()
                ))
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(LocalDateTime.now(), errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            WebRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        List.of(new ErrorResponse.ErrorData(
                                "Request body is missing or invalid",
                                request.getDescription(false)
                        ))
                ));
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFieldNotFound(
            FieldNotFoundException ex,
            WebRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        List.of(new ErrorResponse.ErrorData(
                                ex.getMessage(),
                                "field=" + ex.getFieldName()
                        ))
                ));
    }

    /* ===================== 403 FORBIDDEN ===================== */

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex,
            WebRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        List.of(new ErrorResponse.ErrorData(
                                "Access forbidden",
                                "Insufficient permissions for " + request.getDescription(false)
                        ))
                ));
    }

    /* ===================== 500 INTERNAL ERROR ===================== */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex,
            WebRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        List.of(new ErrorResponse.ErrorData(
                                "Internal server error",
                                request.getDescription(false)
                        ))
                ));
    }
}
