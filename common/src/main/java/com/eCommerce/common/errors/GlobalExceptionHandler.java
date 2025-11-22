package com.eCommerce.common.errors;

import com.eCommerce.common.errors.exceptions.FieldNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                List.of(new ErrorResponse.ErrorData(
                        ex.getMessage(),
                        request.getDescription(false)
                ))
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorResponse.ErrorData> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.ErrorData(
                        error.getDefaultMessage(),
                        "Field: " + error.getField()
                ))
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle missing or unreadable request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                List.of(new ErrorResponse.ErrorData(
                        "Request body is missing or invalid",
                        request.getDescription(false)
                ))
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFieldNotFound(FieldNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                List.of(new ErrorResponse.ErrorData(
                        ex.getMessage(),
                        "Field: " + ex.getFieldName() + " | " + request.getDescription(false)
                ))
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
