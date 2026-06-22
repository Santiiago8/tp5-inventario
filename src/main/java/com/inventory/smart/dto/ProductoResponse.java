package com.inventory.smart.dto;

/**
 * Datos de salida que representan un producto.
 *
 * @param id          identificador del producto
 * @param nombre      nombre del producto
 * @param descripcion descripcion del producto
 * @param precio      precio unitario
 * @param stock       cantidad actual en stock
 * @param categoria   categoria a la que pertenece el producto
 * @author Grupo
 * @since 1.0
 */
public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        double precio,
        int stock,
        CategoriaResponse categoria
) {}