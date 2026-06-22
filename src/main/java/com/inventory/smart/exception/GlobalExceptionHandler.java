package com.inventory.smart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador centralizado de excepciones para toda la API REST.
 *
 * <p>Traduce las excepciones de negocio y validacion en respuestas HTTP
 * con el codigo de estado y el formato de error correctos.</p>
 *
 * @author Grupo
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja el caso de recurso no encontrado.
     *
     * @param ex excepcion capturada
     * @return respuesta 404 con detalle del error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildBody("No encontrado", ex.getMessage()));
    }

    /**
     * Maneja el caso de stock insuficiente para una salida de inventario.
     *
     * @param ex excepcion capturada
     * @return respuesta 409 con detalle del error
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStock(InsufficientStockException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildBody("Stock insuficiente", ex.getMessage()));
    }

    /**
     * Maneja violaciones de reglas de negocio del dominio.
     *
     * @param ex excepcion capturada
     * @return respuesta 409 con detalle del error
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessRule(BusinessRuleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildBody("Regla de negocio violada", ex.getMessage()));
    }

    /**
     * Maneja errores de validacion de los DTOs de entrada (anotaciones Jakarta Validation).
     *
     * @param ex excepcion capturada por Spring al fallar la validacion de un @Valid
     * @return respuesta 400 con el detalle de cada campo invalido
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> body = buildBody("Error de validacion", "Uno o mas campos son invalidos");
        body.put("errores", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private Map<String, Object> buildBody(String error, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", error);
        body.put("mensaje", mensaje);
        body.put("timestamp", LocalDateTime.now());
        return body;
    }
}