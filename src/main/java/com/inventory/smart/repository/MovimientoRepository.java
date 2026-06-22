package com.inventory.smart.repository;

import com.inventory.smart.model.MovimientoInventario;

import java.util.List;

/**
 * Contrato de persistencia especifico para la entidad MovimientoInventario.
 *
 * @author Grupo
 * @since 1.0
 */
public interface MovimientoRepository extends IGenericRepository<MovimientoInventario, Long> {

    /**
     * Obtiene el historial de movimientos de un producto especifico.
     *
     * @param productoId identificador del producto
     * @return lista de movimientos asociados al producto
     */
    List<MovimientoInventario> findByProductoId(Long productoId);
}