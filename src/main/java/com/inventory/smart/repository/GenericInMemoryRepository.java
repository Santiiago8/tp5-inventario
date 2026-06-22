package com.inventory.smart.repository;

import com.inventory.smart.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Implementacion abstracta y reutilizable de {@link IGenericRepository}
 * basada en almacenamiento en memoria.
 *
 * <p>Usa {@link ConcurrentHashMap} como almacenamiento porque es thread-safe
 * sin bloquear todo el mapa (lock striping de Java 8+). Si se usara un
 * {@link java.util.HashMap} comun, accesos concurrentes desde multiples
 * requests HTTP podrian causar condiciones de carrera o
 * {@link java.util.ConcurrentModificationException}.</p>
 *
 * @param <T>  tipo de la entidad gestionada
 * @param <ID> tipo del identificador de la entidad
 * @author Grupo
 * @since 1.0
 */
public abstract class GenericInMemoryRepository<T, ID> implements IGenericRepository<T, ID> {

    /** Almacenamiento interno thread-safe de entidades por id. */
    protected final ConcurrentHashMap<ID, T> dataStore = new ConcurrentHashMap<>();

    /** Generador de identificadores autoincrementales, comienza en 1. */
    protected final AtomicLong idGenerator = new AtomicLong(0);

    private final Function<T, ID> getIdFn;
    private final BiConsumer<T, ID> setIdFn;
    private final Function<Long, ID> idCastFn;

    /**
     * Crea el repositorio en memoria con las funciones necesarias para
     * leer y escribir el identificador de la entidad de forma generica.
     *
     * @param getIdFn  funcion que obtiene el id de una entidad
     * @param setIdFn  funcion que asigna un id a una entidad
     * @param idCastFn funcion que convierte el contador interno (Long) al tipo ID real
     */
    protected GenericInMemoryRepository(Function<T, ID> getIdFn,
                                        BiConsumer<T, ID> setIdFn,
                                        Function<Long, ID> idCastFn) {
        this.getIdFn = getIdFn;
        this.setIdFn = setIdFn;
        this.idCastFn = idCastFn;
    }

    /** {@inheritDoc} */
    @Override
    public List<T> findAll() {
        return List.copyOf(dataStore.values());
    }

    /** {@inheritDoc} */
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dataStore.get(id));
    }

    /** {@inheritDoc} */
    @Override
    public T save(T entity) {
        ID id = getIdFn.apply(entity);
        if (id == null) {
            id = idCastFn.apply(idGenerator.incrementAndGet());
            setIdFn.accept(entity, id);
        }
        dataStore.put(id, entity);
        return entity;
    }

    /** {@inheritDoc} */
    @Override
    public void deleteById(ID id) {
        if (!dataStore.containsKey(id)) {
            throw new ResourceNotFoundException("No existe entidad con id: " + id);
        }
        dataStore.remove(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsById(ID id) {
        return dataStore.containsKey(id);
    }

    /** {@inheritDoc} */
    @Override
    public long count() {
        return dataStore.size();
    }
}