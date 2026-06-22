package com.inventory.smart.service;

import java.util.List;

import com.inventory.smart.model.Categoria;

/**
 * Servicio encargado de la gestion de categorias del inventario.
 *
 * @author Grupo
 * @since 1.0
 */
public interface CategoriaService {

    /**
     * Obtiene todas las categorias registradas.
     *
     * @return lista de categorias
     */
    List<Categoria> findAll();

    /**
     * Busca una categoria por su identificador.
     *
     * @param id identificador de la categoria
     * @return la categoria encontrada
     * @throws com.inventory.smart.exception.ResourceNotFoundException si no existe
     */
    Categoria findById(Long id);

    /**
     * Crea una nueva categoria.
     *
     * @param nombre      nombre de la categoria
     * @param descripcion descripcion opcional
     * @return la categoria creada con su id asignado
     */
    Categoria crear(String nombre, String descripcion);

    /**
     * Actualiza una categoria existente.
     *
     * @param id          identificador de la categoria a actualizar
     * @param nombre      nuevo nombre
     * @param descripcion nueva descripcion
     * @return la categoria actualizada
     * @throws com.inventory.smart.exception.ResourceNotFoundException si no existe
     */
    Categoria actualizar(Long id, String nombre, String descripcion);

    /**
     * Elimina una categoria por su identificador.
     *
     * @param id identificador de la categoria a eliminar
     * @throws com.inventory.smart.exception.ResourceNotFoundException si no existe
     * @throws com.inventory.smart.exception.BusinessRuleException si tiene productos asociados
     */
    void eliminar(Long id);
}