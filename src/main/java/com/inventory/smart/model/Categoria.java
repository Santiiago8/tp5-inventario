package com.inventory.smart.model;

/**
 * Representa una categoria que agrupa productos del inventario.
 *
 * @author Grupo
 * @since 1.0
 */
public class Categoria {

    private Long id;
    private String nombre;
    private String descripcion;

    /**
     * Constructor completo.
     *
     * @param id          identificador unico de la categoria
     * @param nombre      nombre de la categoria
     * @param descripcion descripcion opcional
     */
    public Categoria(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /** @return el identificador unico */
    public Long getId() { return id; }

    /** @param id el identificador a asignar */
    public void setId(Long id) { this.id = id; }

    /** @return el nombre de la categoria */
    public String getNombre() { return nombre; }

    /** @param nombre el nombre a asignar */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return la descripcion de la categoria */
    public String getDescripcion() { return descripcion; }

    /** @param descripcion la descripcion a asignar */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}