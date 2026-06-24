package com.inventory.smart.controller;

import com.inventory.smart.dto.MovimientoRequest;
import com.inventory.smart.dto.MovimientoResponse;
import com.inventory.smart.model.MovimientoInventario;
import com.inventory.smart.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el registro y consulta de movimientos de inventario.
 *
 * @author Grupo
 * @since 1.0
 */
@RestController
@RequestMapping("/api/movimientos")
@Tag(name = "Movimientos", description = "Registro y consulta de movimientos de stock")
public class MovimientoController {

    private final MovimientoService movimientoService;

    /**
     * Crea el controlador inyectando el servicio de movimientos.
     *
     * @param movimientoService servicio de logica de negocio de movimientos
     */
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    /**
     * Lista todos los movimientos registrados.
     *
     * @return 200 con la lista de movimientos
     */
    @GetMapping
    @Operation(summary = "Listar todos los movimientos")
    public ResponseEntity<List<MovimientoResponse>> listar() {
        List<MovimientoResponse> response = movimientoService.findAll().stream()
                .map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene el historial de movimientos de un producto especifico.
     *
     * @param productoId identificador del producto
     * @return 200 con la lista de movimientos del producto
     */
    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener el historial de movimientos de un producto")
    public ResponseEntity<List<MovimientoResponse>> porProducto(@PathVariable Long productoId) {
        List<MovimientoResponse> response = movimientoService.findByProductoId(productoId).stream()
                .map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Registra un nuevo movimiento de inventario (entrada o salida).
     *
     * @param request datos del movimiento a registrar
     * @return 201 con el movimiento registrado, 404 si el producto no existe,
     *         o 409 si es una salida sin stock suficiente
     */
    @PostMapping
    @Operation(summary = "Registrar un movimiento de entrada o salida de stock")
    public ResponseEntity<MovimientoResponse> registrar(@Valid @RequestBody MovimientoRequest request) {
        MovimientoInventario movimiento = movimientoService.registrar(
                request.productoId(), request.tipo(), request.cantidad(), request.motivo());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(movimiento));
    }

    private MovimientoResponse toResponse(MovimientoInventario m) {
        return new MovimientoResponse(
                m.getId(), m.getProductoId(), m.getTipo(),
                m.getCantidad(), m.getStockResultante(), m.getMotivo(), m.getFecha());
    }
}