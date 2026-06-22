package com.inventory.smart.repository;

import com.inventory.smart.model.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementacion en memoria del repositorio de productos.
 *
 * @author Grupo
 * @since 1.0
 */
@Repository
public class InMemoryProductoRepository
        extends GenericInMemoryRepository<Producto, Long>
        implements ProductoRepository {

    /**
     * Crea el repositorio configurando como se lee y asigna el id de Producto.
     */
    public InMemoryProductoRepository() {
        super(Producto::getId, Producto::setId, Long::valueOf);
    }

    /** {@inheritDoc} */
    @Override
    public List<Producto> findByCategoria(Long categoriaId) {
        return dataStore.values().stream()
                .filter(p -> p.getCategoria().getId().equals(categoriaId))
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Producto> buscarPorNombre(String texto) {
        String lower = texto.toLowerCase();
        return dataStore.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(lower))
                .toList();
    }
}