package com.inventory.smart.controller;

import com.inventory.smart.dto.AlertaStockResponse;
import com.inventory.smart.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la consulta de alertas de stock.
 *
 * @author Grupo
 * @since 1.0
 */
@RestController
@RequestMapping("/api/alertas")
@Tag(name = "Alertas", description = "Consulta de alertas de stock bajo o critico")
public class AlertaController {

    private final AlertaService alertaService;

    /**
     * Crea el controlador inyectando el servicio de alertas.
     *
     * @param alertaService servicio de calculo de alertas de stock
     */
    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    /**
     * Obtiene todos los productos con nivel de alerta BAJO o CRITICO.
     *
     * @return 200 con la lista de alertas activas
     */
    @GetMapping
    @Operation(summary = "Listar productos con alerta de stock activa")
    public ResponseEntity<List<AlertaStockResponse>> listarAlertas() {
        return ResponseEntity.ok(alertaService.obtenerAlertasActivas());
    }
}