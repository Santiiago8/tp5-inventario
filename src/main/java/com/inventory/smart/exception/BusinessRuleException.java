package com.inventory.smart.exception;

/**
 * Excepcion lanzada cuando se viola una regla de negocio del dominio,
 * por ejemplo eliminar una categoria con productos asociados.
 *
 * @author Grupo
 * @since 1.0
 */
public class BusinessRuleException extends RuntimeException {

    /**
     * Crea la excepcion con un mensaje descriptivo.
     *
     * @param mensaje descripcion de la regla de negocio violada
     */
    public BusinessRuleException(String mensaje) {
        super(mensaje);
    }
}