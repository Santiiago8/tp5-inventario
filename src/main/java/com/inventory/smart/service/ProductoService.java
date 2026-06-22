package com.inventory.smart.service;

import java.util.List;

import com.inventory.smart.model.Producto;

/**
 * Servicio encargado de la gestion de productos del inventario.
 *
 * <p>Proporciona operaciones CRUD, busqueda textual y ordenamiento
 * parametrizado de productos. Aplica las reglas de negocio definidas
 * para la creacion y modificacion de productos.</p>
 *
 * @author Grupo
 * @since 1.0
 */
public interface ProductoService {

    /**
     * Obtiene todos los productos, con filtros opcionales.
     *
     * @param categoriaId filtro por categoria, puede ser null
     * @param precioMin   filtro de precio minimo, puede ser null
     * @param precioMax   filtro de precio maximo, puede ser null
     * @param enStock     si es true, filtra solo productos con stock mayor a 0
     * @return lista de productos que cumplen los filtros
     */
    List<Producto> findAll(Long categoriaId, Double precioMin, Double precioMax, Boolean enStock);

    /**
     * Busca un producto por su identificador unico.
     *
     * @param id identificador del producto
     * @return el producto encontrado
     * @throws com.inventory.smart.exception.ResourceNotFoundException si no existe producto con el id dado
     */
    Producto findById(Long id);

    /**
     * Crea un nuevo producto.
     *
     * @param nombre       nombre del producto
     * @param descripcion  descripcion opcional
     * @param precio       precio unitario, debe ser mayor o igual a 0
     * @param stockInicial stock inicial, debe ser mayor o igual a 0
     * @param categoriaId  identificador de la categoria asociada
     * @return el producto creado con su id asignado
     * @throws com.inventory.smart.exception.ResourceNotFoundException si la categoria no existe
     */
    Producto crear(String nombre, String descripcion, double precio, int stockInicial, Long categoriaId);

    /**
     * Actualiza un producto existente. Los campos nulos no se modifican.
     *
     * @param id          identificador del producto a actualizar
     * @param nombre      nuevo nombre, puede ser null
     * @param descripcion nueva descripcion, puede ser null
     * @param precio      nuevo precio, puede ser null
     * @param categoriaId nueva categoria, puede ser null
     * @return el producto actualizado
     * @throws com.inventory.smart.exception.ResourceNotFoundException si el producto o la categoria no existen
     */
    Producto actualizar(Long id, String nombre, String descripcion, Double precio, Long categoriaId);

    /**
     * Elimina un producto por su identificador.
     *
     * @param id identificador del producto a eliminar
     * @throws com.inventory.smart.exception.ResourceNotFoundException si no existe
     */
    void eliminar(Long id);

    /**
     * Busca productos cuyo nombre contenga el texto dado.
     *
     * @param texto texto a buscar, no puede estar vacio
     * @return lista de productos que coinciden
     */
    List<Producto> buscarPorNombre(String texto);

    /**
     * Lista productos ordenados por un campo especifico.
     *
     * @param campo campo de ordenamiento: nombre, precio o stock
     * @param orden direccion del orden: asc o desc
     * @return lista de productos ordenada
     */
    List<Producto> listarOrdenados(String campo, String orden);
}