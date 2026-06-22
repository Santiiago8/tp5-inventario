package com.inventory.smart.exception;

/**
 * Excepcion lanzada cuando una entidad solicitada no existe.
 *
 * @author Grupo
 * @since 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Crea la excepcion con un mensaje descriptivo.
     *
     * @param mensaje descripcion del recurso no encontrado
     */
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}