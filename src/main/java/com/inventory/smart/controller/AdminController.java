package com.inventory.smart.controller;

import com.inventory.smart.dto.PerformanceResultado;
import com.inventory.smart.service.PerformanceReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para operaciones administrativas, como la generacion
 * del reporte de rendimiento del repositorio generico.
 *
 * @author Grupo
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Operaciones administrativas y de diagnostico")
public class AdminController {

    private final PerformanceReportService performanceReportService;

    /**
     * Crea el controlador inyectando el servicio de reporte de rendimiento.
     *
     * @param performanceReportService servicio que ejecuta las mediciones
     */
    public AdminController(PerformanceReportService performanceReportService) {
        this.performanceReportService = performanceReportService;
    }

    /**
     * Ejecuta y devuelve las mediciones de rendimiento del repositorio generico
     * para volumenes de 1.000, 10.000 y 100.000 registros.
     *
     * <p><strong>Advertencia:</strong> este endpoint ejecuta cargas masivas de datos
     * de prueba y puede tardar varios segundos en responder.</p>
     *
     * @return 200 con los resultados de las mediciones por volumen
     */
    @GetMapping("/performance-report")
    @Operation(summary = "Ejecutar el reporte de rendimiento del repositorio generico")
    public ResponseEntity<List<PerformanceResultado>> obtenerReporte() {
        return ResponseEntity.ok(performanceReportService.ejecutarMediciones());
    }
}