package com.inventory.smart.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa un producto del inventario del deposito.
 *
 * <p>El stock se gestiona con {@link AtomicInteger} para garantizar
 * la consistencia ante accesos concurrentes desde multiples requests HTTP.</p>
 *
 * @author Grupo
 * @since 1.0
 */
public class Producto {

    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private final AtomicInteger stock;
    private Categoria categoria;

    /**
     * Constructor completo.
     *
     * @param id          identificador unico del producto
     * @param nombre      nombre del producto
     * @param descripcion descripcion opcional
     * @param precio      precio unitario, debe ser mayor o igual a 0
     * @param stockInicial cantidad inicial en inventario, debe ser mayor o igual a 0
     * @param categoria   categoria a la que pertenece el producto
     */
    public Producto(Long id, String nombre, String descripcion,
                    double precio, int stockInicial, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = new AtomicInteger(stockInicial);
        this.categoria = categoria;
    }

    /**
     * Incrementa el stock del producto de forma atómica.
     *
     * @param cantidad cantidad a agregar, debe ser positiva
     * @return el nuevo valor del stock tras el incremento
     */
    public int incrementarStock(int cantidad) {
        return stock.addAndGet(cantidad);
    }

    /**
     * Decrementa el stock del producto de forma atomica.
     *
     * @param cantidad cantidad a retirar, debe ser positiva
     * @return el nuevo valor del stock tras el decremento
     */
    public int decrementarStock(int cantidad) {
        return stock.addAndGet(-cantidad);
    }

    /** @return el identificador unico */
    public Long getId() { return id; }

    /** @param id el identificador a asignar */
    public void setId(Long id) { this.id = id; }

    /** @return el nombre del producto */
    public String getNombre() { return nombre; }

    /** @param nombre el nombre a asignar */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return la descripcion del producto */
    public String getDescripcion() { return descripcion; }

    /** @param descripcion la descripcion a asignar */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** @return el precio unitario */
    public double getPrecio() { return precio; }

    /** @param precio el precio a asignar */
    public void setPrecio(double precio) { this.precio = precio; }

    /** @return el stock actual */
    public int getStock() { return stock.get(); }

    /** @return la categoria del producto */
    public Categoria getCategoria() { return categoria; }

    /** @param categoria la categoria a asignar */
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}