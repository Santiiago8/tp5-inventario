package com.inventory.smart.model;

import java.time.LocalDateTime;

/**
 * Representa un movimiento de inventario (entrada o salida de stock).
 *
 * <p>Cada movimiento queda registrado con el producto afectado,
 * el tipo de operacion, la cantidad y el stock resultante.</p>
 *
 * @author Grupo
 * @since 1.0
 */
public class MovimientoInventario {

    private Long id;
    private Long productoId;
    private TipoMovimiento tipo;
    private int cantidad;
    private int stockResultante;
    private String motivo;
    private LocalDateTime fecha;

    /**
     * Constructor completo.
     *
     * @param id               identificador unico del movimiento
     * @param productoId       identificador del producto afectado
     * @param tipo             tipo de movimiento (ENTRADA o SALIDA)
     * @param cantidad         cantidad de unidades involucradas
     * @param stockResultante  stock del producto tras el movimiento
     * @param motivo           descripción del motivo del movimiento
     * @param fecha            fecha y hora del movimiento
     */
    public MovimientoInventario(Long id, Long productoId, TipoMovimiento tipo,
                                int cantidad, int stockResultante,
                                String motivo, LocalDateTime fecha) {
        this.id = id;
        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.stockResultante = stockResultante;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    /** @return el identificador unico */
    public Long getId() { return id; }

    /** @param id el identificador a asignar */
    public void setId(Long id) { this.id = id; }

    /** @return el identificador del producto afectado */
    public Long getProductoId() { return productoId; }

    /** @param productoId el identificador del producto */
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    /** @return el tipo de movimiento */
    public TipoMovimiento getTipo() { return tipo; }

    /** @param tipo el tipo de movimiento a asignar */
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }

    /** @return la cantidad de unidades del movimiento */
    public int getCantidad() { return cantidad; }

    /** @param cantidad la cantidad a asignar */
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    /** @return el stock resultante tras el movimiento */
    public int getStockResultante() { return stockResultante; }

    /** @param stockResultante el stock resultante a asignar */
    public void setStockResultante(int stockResultante) { this.stockResultante = stockResultante; }

    /** @return el motivo del movimiento */
    public String getMotivo() { return motivo; }

    /** @param motivo el motivo a asignar */
    public void setMotivo(String motivo) { this.motivo = motivo; }

    /** @return la fecha y hora del movimiento */
    public LocalDateTime getFecha() { return fecha; }

    /** @param fecha la fecha a asignar */
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}