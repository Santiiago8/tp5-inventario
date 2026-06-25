package com.inventory.smart.service;

import com.inventory.smart.exception.InsufficientStockException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.MovimientoInventario;
import com.inventory.smart.model.Producto;
import com.inventory.smart.model.TipoMovimiento;
import com.inventory.smart.repository.InMemoryMovimientoRepository;
import com.inventory.smart.repository.InMemoryProductoRepository;
import com.inventory.smart.repository.MovimientoRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para {@link MovimientoServiceImpl}, enfocados en la
 * regla de negocio critica de validacion de stock.
 *
 * @author Grupo
 * @since 1.0
 */
class MovimientoServiceImplTest {

    private ProductoRepository productoRepository;
    private MovimientoRepository movimientoRepository;
    private MovimientoService movimientoService;
    private Long productoId;

    /**
     * Prepara un escenario limpio con un producto de stock conocido
     * antes de cada test.
     */
    @BeforeEach
    void setUp() {
        productoRepository = new InMemoryProductoRepository();
        movimientoRepository = new InMemoryMovimientoRepository();
        movimientoService = new MovimientoServiceImpl(movimientoRepository, productoRepository);

        Categoria categoria = new Categoria(1L, "Test", "Categoria de prueba");
        Producto producto = new Producto(null, "Producto Test", "desc", 100.0, 10, categoria);
        productoId = productoRepository.save(producto).getId();
    }

    /**
     * Verifica que una SALIDA con cantidad mayor al stock disponible
     * lance InsufficientStockException y no modifique el stock.
     */
    @Test
    void registrarSalidaConStockInsuficienteLanzaExcepcion() {
        assertThrows(InsufficientStockException.class, () ->
                movimientoService.registrar(productoId, TipoMovimiento.SALIDA, 9999, "Venta masiva"));

        Producto producto = productoRepository.findById(productoId).orElseThrow();
        assertEquals(10, producto.getStock(), "El stock no debe modificarse si la operacion falla");
    }

    /**
     * Verifica que una SALIDA con cantidad exacta al stock disponible
     * sea valida y deje el stock en 0.
     */
    @Test
    void registrarSalidaConStockExactoEsValida() {
        MovimientoInventario movimiento = movimientoService.registrar(
                productoId, TipoMovimiento.SALIDA, 10, "Venta total");

        assertEquals(0, movimiento.getStockResultante());
    }

    /**
     * Verifica que una ENTRADA incremente correctamente el stock.
     */
    @Test
    void registrarEntradaIncrementaStock() {
        MovimientoInventario movimiento = movimientoService.registrar(
                productoId, TipoMovimiento.ENTRADA, 5, "Reposicion");

        assertEquals(15, movimiento.getStockResultante());
    }

    /**
     * Verifica que registrar un movimiento sobre un producto inexistente
     * lance ResourceNotFoundException.
     */
    @Test
    void registrarMovimientoConProductoInexistenteLanzaExcepcion() {
        assertThrows(ResourceNotFoundException.class, () ->
                movimientoService.registrar(999L, TipoMovimiento.ENTRADA, 5, "Test"));
    }
}