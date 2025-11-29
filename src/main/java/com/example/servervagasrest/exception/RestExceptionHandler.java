package com.example.servervagasrest.exception;

import com.example.servervagasrest.controller.dto.FieldErrorDetail;
import com.example.servervagasrest.controller.dto.ValidationErrorResponse;
import com.example.servervagasrest.exception.UsernameConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.servervagasrest.exception.ImmutableFieldException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorDetail(error.getField(), error.getDefaultMessage()))
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                "Validation error",
                "UNPROCESSABLE",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UsernameConflictException.class)
    public ResponseEntity<Map<String, String>> handleUsernameConflict(UsernameConflictException ex) {
        Map<String, String> response = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> response = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleJobNotFound(JobNotFoundException ex)
    {
        Map<String, String> response = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<Map<String, String>> handleForbiddenAccess(ForbiddenAccessException ex) {
        Map<String, String> response = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
        Map<String, String> response = Map.of("message", "Invalid credentials");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ManualValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleManualValidationExceptions(ManualValidationException ex) {
        List<FieldErrorDetail> errors = List.of(new FieldErrorDetail(ex.getField(), ex.getMessage()));

        ValidationErrorResponse response = new ValidationErrorResponse(
                "Validation error",
                "UNPROCESSABLE",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ImmutableFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, Object> handleImmutableField(ImmutableFieldException ex) {
        return Map.of(
                "message", "Erro de validação",
                "code", "UNPROCESSABLE",
                "details", List.of(Map.of("field", "username", "error", "immutable_field"))
        );
    }
}