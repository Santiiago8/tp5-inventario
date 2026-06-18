package com.inventory.smart.model;

/**
 * Representa el nivel de alerta de stock de un producto.
 *
 * @author Grupo
 * @since 1.0
 */
public enum NivelAlerta {
    /** Stock mayor o igual al minimo configurado. */
    NORMAL,
    /** Stock por debajo del minimo pero por encima del critico. */
    BAJO,
    /** Stock por debajo del umbral critico configurado. */
    CRITICO
}