package com.inventory.smart.dto;

import com.inventory.smart.model.TipoMovimiento;

import java.time.LocalDateTime;

/**
 * Datos de salida que representan un movimiento de inventario registrado.
 *
 * @param id              identificador del movimiento
 * @param productoId      identificador del producto afectado
 * @param tipo            tipo de movimiento, ENTRADA o SALIDA
 * @param cantidad        cantidad de unidades del movimiento
 * @param stockResultante stock del producto luego del movimiento
 * @param motivo          motivo del movimiento
 * @param fecha           fecha y hora en que se registro el movimiento
 * @author Grupo
 * @since 1.0
 */
public record MovimientoResponse(
        Long id,
        Long productoId,
        TipoMovimiento tipo,
        int cantidad,
        int stockResultante,
        String motivo,
        LocalDateTime fecha
) {}