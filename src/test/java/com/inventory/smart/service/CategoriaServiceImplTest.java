package com.inventory.smart.service;

import com.inventory.smart.exception.BusinessRuleException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.InMemoryCategoriaRepository;
import com.inventory.smart.repository.InMemoryProductoRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para {@link CategoriaServiceImpl}, enfocados en la
 * regla de integridad referencial al eliminar categorias.
 *
 * @author Grupo
 * @since 1.0
 */
class CategoriaServiceImplTest {

    private CategoriaRepository categoriaRepository;
    private ProductoRepository productoRepository;
    private CategoriaService categoriaService;

    /**
     * Prepara un escenario limpio antes de cada test.
     */
    @BeforeEach
    void setUp() {
        categoriaRepository = new InMemoryCategoriaRepository();
        productoRepository = new InMemoryProductoRepository();
        categoriaService = new CategoriaServiceImpl(categoriaRepository, productoRepository);
    }

    /**
     * Verifica que eliminar una categoria con productos asociados
     * lance BusinessRuleException.
     */
    @Test
    void eliminarCategoriaConProductosLanzaExcepcion() {
        Categoria categoria = categoriaService.crear("Electronica", "Categoria de prueba");
        productoRepository.save(new Producto(null, "Mouse", "desc", 100.0, 5, categoria));

        assertThrows(BusinessRuleException.class, () ->
                categoriaService.eliminar(categoria.getId()));
    }

    /**
     * Verifica que eliminar una categoria sin productos asociados
     * se complete sin lanzar excepciones.
     */
    @Test
    void eliminarCategoriaSinProductosEsValido() {
        Categoria categoria = categoriaService.crear("Vacia", "Sin productos");

        assertDoesNotThrow(() -> categoriaService.eliminar(categoria.getId()));
        assertFalse(categoriaRepository.existsById(categoria.getId()));
    }

    /**
     * Verifica que buscar una categoria por un id inexistente
     * lance ResourceNotFoundException.
     */
    @Test
    void buscarCategoriaInexistenteLanzaExcepcion() {
        assertThrows(ResourceNotFoundException.class, () ->
                categoriaService.findById(999L));
    }
}