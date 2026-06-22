package com.inventory.smart.dto;

import com.inventory.smart.model.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Datos de entrada para registrar un movimiento de inventario.
 *
 * @param productoId identificador del producto afectado, obligatorio
 * @param tipo       tipo de movimiento, ENTRADA o SALIDA
 * @param cantidad   cantidad de unidades, debe ser mayor a 0
 * @param motivo     descripcion del motivo del movimiento
 * @author Grupo
 * @since 1.0
 */
public record MovimientoRequest(

        @NotNull(message = "El producto es obligatorio")
        Long productoId,

        @NotNull(message = "El tipo de movimiento es obligatorio")
        TipoMovimiento tipo,

        @Positive(message = "La cantidad debe ser mayor a 0")
        int cantidad,

        @Size(max = 200)
        String motivo
) {}