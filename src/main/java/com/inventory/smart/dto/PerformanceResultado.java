package com.inventory.smart.dto;

import java.util.List;

/**
 * Resultado de las mediciones de rendimiento sobre operaciones del repositorio.
 *
 * @param cantidadRegistros cantidad de registros usados en la medicion
 * @param mediciones        lista de mediciones individuales por operacion
 * @author Grupo
 * @since 1.0
 */
public record PerformanceResultado(
        int cantidadRegistros,
        List<MedicionOperacion> mediciones
) {

    /**
     * Representa el tiempo medido para una operacion especifica.
     *
     * @param operacion       nombre de la operacion medida
     * @param tiempoNanos     tiempo de ejecucion en nanosegundos
     * @param tiempoMilis     tiempo de ejecucion en milisegundos
     * @author Grupo
     * @since 1.0
     */
    public record MedicionOperacion(
            String operacion,
            long tiempoNanos,
            double tiempoMilis
    ) {}
}