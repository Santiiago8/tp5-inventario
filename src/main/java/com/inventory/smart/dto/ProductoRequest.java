package com.inventory.smart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Datos de entrada para crear o actualizar un producto.
 *
 * @param nombre       nombre del producto, obligatorio
 * @param descripcion  descripcion opcional del producto
 * @param precio       precio unitario, debe ser mayor o igual a 0
 * @param stockInicial stock inicial, debe ser mayor o igual a 0
 * @param categoriaId  identificador de la categoria a la que pertenece
 * @author Grupo
 * @since 1.0
 */
public record ProductoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100)
        String nombre,

        @Size(max = 500)
        String descripcion,

        @PositiveOrZero(message = "El precio debe ser >= 0")
        double precio,

        @PositiveOrZero(message = "El stock inicial debe ser >= 0")
        int stockInicial,

        @NotNull(message = "La categoria es obligatoria")
        Long categoriaId
) {}