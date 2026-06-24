package com.inventory.smart.service;

import com.inventory.smart.exception.InsufficientStockException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.MovimientoInventario;
import com.inventory.smart.model.Producto;
import com.inventory.smart.model.TipoMovimiento;
import com.inventory.smart.repository.MovimientoRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del servicio de movimientos de inventario.
 *
 * <p>Centraliza la regla de negocio mas critica del sistema: una SALIDA
 * de stock solo puede registrarse si el producto tiene stock suficiente.
 * El chequeo y el descuento se hacen sobre el mismo {@link Producto},
 * cuyo stock interno usa {@link java.util.concurrent.atomic.AtomicInteger}
 * para que la operacion sea segura ante concurrencia.</p>
 *
 * @author Grupo
 * @since 1.0
 */
@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    /**
     * Crea el servicio inyectando sus dependencias por constructor.
     *
     * @param movimientoRepository repositorio de movimientos
     * @param productoRepository   repositorio de productos, usado para leer y actualizar el stock
     */
    public MovimientoServiceImpl(MovimientoRepository movimientoRepository,
                                 ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    /** {@inheritDoc} */
    @Override
    public MovimientoInventario registrar(Long productoId, TipoMovimiento tipo, int cantidad, String motivo) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe producto con id: " + productoId));

        int stockResultante;

        if (tipo == TipoMovimiento.SALIDA) {
            if (producto.getStock() < cantidad) {
                throw new InsufficientStockException(
                        "Stock insuficiente para el producto " + productoId +
                        ". Disponible: " + producto.getStock() + ", solicitado: " + cantidad);
            }
            stockResultante = producto.decrementarStock(cantidad);
        } else {
            stockResultante = producto.incrementarStock(cantidad);
        }

        productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario(
                null, productoId, tipo, cantidad, stockResultante, motivo, LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }

    /** {@inheritDoc} */
    @Override
    public List<MovimientoInventario> findAll() {
        return movimientoRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public List<MovimientoInventario> findByProductoId(Long productoId) {
        return movimientoRepository.findByProductoId(productoId);
    }
}