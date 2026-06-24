package com.inventory.smart.service;

import com.inventory.smart.dto.AlertaStockResponse;

import java.util.List;

/**
 * Servicio encargado de calcular y exponer las alertas de stock activas.
 *
 * @author Grupo
 * @since 1.0
 */
public interface AlertaService {

    /**
     * Obtiene todos los productos cuyo nivel de alerta sea BAJO o CRITICO.
     *
     * @return lista de alertas activas
     */
    List<AlertaStockResponse> obtenerAlertasActivas();
}