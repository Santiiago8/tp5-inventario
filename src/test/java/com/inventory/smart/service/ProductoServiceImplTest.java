package com.inventory.smart.service;

import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.InMemoryCategoriaRepository;
import com.inventory.smart.repository.InMemoryProductoRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para {@link ProductoServiceImpl}, enfocados en la
 * busqueda de productos y validacion de categoria asociada.
 *
 * @author Grupo
 * @since 1.0
 */
class ProductoServiceImplTest {

    private ProductoRepository productoRepository;
    private CategoriaRepository categoriaRepository;
    private ProductoService productoService;

    /**
     * Prepara un escenario limpio antes de cada test.
     */
    @BeforeEach
    void setUp() {
        productoRepository = new InMemoryProductoRepository();
        categoriaRepository = new InMemoryCategoriaRepository();
        productoService = new ProductoServiceImpl(productoRepository, categoriaRepository);
    }

    /**
     * Verifica que buscar un producto por un id inexistente
     * lance ResourceNotFoundException.
     */
    @Test
    void buscarProductoInexistenteLanzaExcepcion() {
        assertThrows(ResourceNotFoundException.class, () ->
                productoService.findById(999L));
    }

    /**
     * Verifica que crear un producto con una categoria inexistente
     * lance ResourceNotFoundException.
     */
    @Test
    void crearProductoConCategoriaInexistenteLanzaExcepcion() {
        assertThrows(ResourceNotFoundException.class, () ->
                productoService.crear("Mouse", "desc", 100.0, 10, 999L));
    }

    /**
     * Verifica que crear un producto con una categoria valida
     * se complete correctamente.
     */
    @Test
    void crearProductoConCategoriaValidaEsCorrecto() {
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Test", "desc"));

        var producto = productoService.crear("Mouse", "desc", 100.0, 10, categoria.getId());

        assertNotNull(producto.getId());
        assertEquals(10, producto.getStock());
        assertEquals(categoria.getId(), producto.getCategoria().getId());
    }
}