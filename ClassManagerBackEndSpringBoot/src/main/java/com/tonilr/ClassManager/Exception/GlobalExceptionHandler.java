package com.tonilr.ClassManager.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===========================================
    // MANEJADOR DE VALIDACIÓN (SOLO LO ESENCIAL)
    // ===========================================
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        Map<String, Object> response = Map.of(
            "error", "VALIDATION_ERROR",
            "message", "Validation failed",
            "details", errors
        );
        
        return ResponseEntity.badRequest().body(response);
    }

    // ===========================================
    // MANEJADOR DE TUS RuntimeException REALES
    // ===========================================
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        
        // Detectar automáticamente el tipo de error por el mensaje
        HttpStatus status;
        String errorType;
        
        if (message.contains("not found")) {
            status = HttpStatus.NOT_FOUND;
            errorType = "NOT_FOUND";
        } else if (message.contains("Unauthorized")) {
            status = HttpStatus.FORBIDDEN;
            errorType = "ACCESS_DENIED";
        } else {
            status = HttpStatus.BAD_REQUEST;
            errorType = "BAD_REQUEST";
        }
        
        Map<String, Object> response = Map.of(
            "error", errorType,
            "message", message
        );
        
        return ResponseEntity.status(status).body(response);
    }

    // ===========================================
    // FALLBACK GENÉRICO (SOLO PARA EMERGENCIAS)
    // ===========================================
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> response = Map.of(
            "error", "INTERNAL_ERROR",
            "message", "Internal server error"
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
} 