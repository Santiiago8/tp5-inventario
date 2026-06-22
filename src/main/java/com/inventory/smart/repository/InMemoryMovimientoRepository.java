package com.inventory.smart.repository;

import com.inventory.smart.model.MovimientoInventario;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementacion en memoria del repositorio de movimientos de inventario.
 *
 * @author Grupo
 * @since 1.0
 */
@Repository
public class InMemoryMovimientoRepository
        extends GenericInMemoryRepository<MovimientoInventario, Long>
        implements MovimientoRepository {

    /**
     * Crea el repositorio configurando como se lee y asigna el id de MovimientoInventario.
     */
    public InMemoryMovimientoRepository() {
        super(MovimientoInventario::getId, MovimientoInventario::setId, Long::valueOf);
    }

    /** {@inheritDoc} */
    @Override
    public List<MovimientoInventario> findByProductoId(Long productoId) {
        return dataStore.values().stream()
                .filter(m -> m.getProductoId().equals(productoId))
                .toList();
    }
}