package com.inventory.smart.service;

import com.inventory.smart.model.MovimientoInventario;
import com.inventory.smart.model.TipoMovimiento;

import java.util.List;

/**
 * Servicio encargado de registrar y consultar movimientos de inventario.
 *
 * @author Grupo
 * @since 1.0
 */
public interface MovimientoService {

    /**
     * Registra un nuevo movimiento de inventario y actualiza el stock del producto.
     *
     * @param productoId identificador del producto afectado
     * @param tipo       tipo de movimiento, ENTRADA o SALIDA
     * @param cantidad   cantidad de unidades, debe ser mayor a 0
     * @param motivo     motivo del movimiento
     * @return el movimiento registrado, con el stock resultante
     * @throws com.inventory.smart.exception.ResourceNotFoundException si el producto no existe
     * @throws com.inventory.smart.exception.InsufficientStockException si es una SALIDA y no hay stock suficiente
     */
    MovimientoInventario registrar(Long productoId, TipoMovimiento tipo, int cantidad, String motivo);

    /**
     * Obtiene todos los movimientos registrados.
     *
     * @return lista de movimientos
     */
    List<MovimientoInventario> findAll();

    /**
     * Obtiene el historial de movimientos de un producto especifico.
     *
     * @param productoId identificador del producto
     * @return lista de movimientos asociados al producto
     */
    List<MovimientoInventario> findByProductoId(Long productoId);
}