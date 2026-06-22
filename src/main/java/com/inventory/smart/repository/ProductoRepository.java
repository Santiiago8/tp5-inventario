package com.inventory.smart.repository;

import com.inventory.smart.model.Producto;

import java.util.List;

/**
 * Contrato de persistencia especifico para la entidad Producto.
 *
 * @author Grupo
 * @since 1.0
 */
public interface ProductoRepository extends IGenericRepository<Producto, Long> {

    /**
     * Busca productos que pertenezcan a una categoria especifica.
     *
     * @param categoriaId identificador de la categoria
     * @return lista de productos asociados a la categoria
     */
    List<Producto> findByCategoria(Long categoriaId);

    /**
     * Busca productos cuyo nombre contenga el texto dado (sin distincion de mayusculas).
     *
     * @param texto texto a buscar dentro del nombre del producto
     * @return lista de productos que coinciden con la busqueda
     */
    List<Producto> buscarPorNombre(String texto);
}