package com.hardware.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice: Convierte esta clase en el "Centro de Quejas" de toda tu aplicación.
// Cualquier error que ocurra en cualquier Controller, vendrá aquí primero.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- CASO 1: Alguien rompió las reglas del DTO (@Valid) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // Creamos un mapa (Diccionario) para guardar los errores limpios.
        // Ejemplo: "email" -> "El formato es inválido"
        Map<String, String> errors = new HashMap<>();

        // Spring nos da una lista compleja de errores. Aquí la recorremos
        // para sacar solo lo que nos importa: ¿Qué campo falló? y ¿Qué mensaje mostramos?
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        // Devolvemos el mapa limpio con un error 400 (Bad Request)
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // --- CASO 2: Algo no se encontró en la Base de Datos ---
    // Como ya tenías la clase ResourceNotFoundException, aprovechamos para manejarla aquí también.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex){
        // Devuelve solo el mensaje (ej: "User not found") y un error 404
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // ... tus otros handlers

    // Manejar falta de Stock (Devuelve 400 Bad Request o 409 Conflict)
    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<String> handleOutOfStock(OutOfStockException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}