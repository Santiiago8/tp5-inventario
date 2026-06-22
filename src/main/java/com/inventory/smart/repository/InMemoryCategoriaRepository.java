package com.inventory.smart.repository;

import com.inventory.smart.model.Categoria;
import org.springframework.stereotype.Repository;

/**
 * Implementacion en memoria del repositorio de categorias.
 *
 * <p>No agrega queries adicionales: hereda todo el comportamiento
 * CRUD base de {@link GenericInMemoryRepository}.</p>
 *
 * @author Grupo
 * @since 1.0
 */
@Repository
public class InMemoryCategoriaRepository
        extends GenericInMemoryRepository<Categoria, Long>
        implements CategoriaRepository {

    /**
     * Crea el repositorio configurando como se lee y asigna el id de Categoria.
     */
    public InMemoryCategoriaRepository() {
        super(Categoria::getId, Categoria::setId, Long::valueOf);
    }
}