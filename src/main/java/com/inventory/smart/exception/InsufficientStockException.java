package com.inventory.smart.exception;

/**
 * Excepcion lanzada cuando no hay suficiente stock disponible.
 *
 * @author Grupo
 * @since 1.0
 */
public class InsufficientStockException extends RuntimeException {

    /**
     * Crea la excepcion con un mensaje descriptivo.
     *
     * @param message descripcion de la falta de stock
     */
    public InsufficientStockException(String message) {
        super(message);
    }
}