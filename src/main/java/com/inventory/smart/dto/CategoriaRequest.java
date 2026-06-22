package com.inventory.smart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos de entrada para crear o actualizar una categoria.
 *
 * @param nombre      nombre de la categoria, obligatorio
 * @param descripcion descripcion opcional de la categoria
 * @author Grupo
 * @since 1.0
 */
public record CategoriaRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100)
        String nombre,

        @Size(max = 500)
        String descripcion
) {}