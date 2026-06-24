package com.inventory.smart.service;

import com.inventory.smart.dto.AlertaStockResponse;
import com.inventory.smart.model.NivelAlerta;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacion del servicio de alertas de stock.
 *
 * <p>Delega el calculo del nivel de alerta en una {@link AlertaStrategy}
 * inyectada, sin conocer los detalles de como se calcula cada nivel.</p>
 *
 * @author Grupo
 * @since 1.0
 */
@Service
public class AlertaServiceImpl implements AlertaService {

    private final ProductoRepository productoRepository;
    private final AlertaStrategy alertaStrategy;

    /**
     * Crea el servicio inyectando el repositorio y la estrategia de alerta.
     *
     * @param productoRepository repositorio de productos
     * @param alertaStrategy     estrategia usada para evaluar el nivel de alerta de cada producto
     */
    public AlertaServiceImpl(ProductoRepository productoRepository, AlertaStrategy alertaStrategy) {
        this.productoRepository = productoRepository;
        this.alertaStrategy = alertaStrategy;
    }

    /** {@inheritDoc} */
    @Override
    public List<AlertaStockResponse> obtenerAlertasActivas() {
        return productoRepository.findAll().stream()
                .map(this::evaluarProducto)
                .filter(alerta -> alerta.nivel() != NivelAlerta.NORMAL)
                .toList();
    }

    private AlertaStockResponse evaluarProducto(Producto producto) {
        NivelAlerta nivel = alertaStrategy.evaluar(producto);
        return new AlertaStockResponse(producto.getId(), producto.getNombre(), producto.getStock(), nivel);
    }
}