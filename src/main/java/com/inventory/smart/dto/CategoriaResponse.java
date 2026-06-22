package com.inventory.smart.dto;

/**
 * Datos de salida que representan una categoria.
 *
 * @param id          identificador de la categoria
 * @param nombre      nombre de la categoria
 * @param descripcion descripcion de la categoria
 * @author Grupo
 * @since 1.0
 */
public record CategoriaResponse(
        Long id,
        String nombre,
        String descripcion
) {}