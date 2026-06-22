package com.inventory.smart.dto;

import com.inventory.smart.model.NivelAlerta;

/**
 * Datos de salida que representan una alerta de stock bajo o critico.
 *
 * @param productoId   identificador del producto en alerta
 * @param nombre       nombre del producto
 * @param stockActual  stock actual del producto
 * @param nivel        nivel de alerta, BAJO o CRITICO
 * @author Grupo
 * @since 1.0
 */
public record AlertaStockResponse(
        Long productoId,
        String nombre,
        int stockActual,
        NivelAlerta nivel
) {}