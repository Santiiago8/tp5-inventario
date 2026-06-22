package com.inventory.smart.repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz generica para repositorios que define las operaciones CRUD basicas.
 *
 * @param <T>  tipo de la entidad gestionada
 * @param <ID> tipo del identificador de la entidad
 * @author Grupo
 * @since 1.0
 */
public interface IGenericRepository<T, ID> {

    /**
     * Obtiene todas las entidades almacenadas.
     *
     * @return lista con todas las entidades
     */
    List<T> findAll();

    /**
     * Busca una entidad por su identificador.
     *
     * @param id identificador de la entidad
     * @return un Optional con la entidad si existe, vacio si no
     */
    Optional<T> findById(ID id);

    /**
     * Guarda una entidad nueva o actualiza una existente.
     *
     * @param entity entidad a guardar
     * @return la entidad guardada, con su id asignado si era nueva
     */
    T save(T entity);

    /**
     * Elimina una entidad por su identificador.
     *
     * @param id identificador de la entidad a eliminar
     * @throws com.inventory.smart.exception.ResourceNotFoundException si no existe
     */
    void deleteById(ID id);

    /**
     * Verifica si existe una entidad con el identificador dado.
     *
     * @param id identificador a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsById(ID id);

    /**
     * Cuenta la cantidad total de entidades almacenadas.
     *
     * @return cantidad de entidades
     */
    long count();
}