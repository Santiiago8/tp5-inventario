package com.inventory.smart.controller;

import com.inventory.smart.dto.CategoriaRequest;
import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestion de categorias del inventario.
 *
 * @author Grupo
 * @since 1.0
 */
@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Operaciones sobre categorias de productos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Crea el controlador inyectando el servicio de categorias.
     *
     * @param categoriaService servicio de logica de negocio de categorias
     */
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Lista todas las categorias registradas.
     *
     * @return 200 con la lista de categorias
     */
    @GetMapping
    @Operation(summary = "Listar todas las categorias")
    public ResponseEntity<List<CategoriaResponse>> listar() {
        List<CategoriaResponse> response = categoriaService.findAll().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una categoria por su identificador.
     *
     * @param id identificador de la categoria
     * @return 200 con la categoria encontrada, o 404 si no existe
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una categoria por id")
    public ResponseEntity<CategoriaResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(categoriaService.findById(id)));
    }

    /**
     * Crea una nueva categoria.
     *
     * @param request datos de la categoria a crear
     * @return 201 con la categoria creada
     */
    @PostMapping
    @Operation(summary = "Crear una nueva categoria")
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request) {
        Categoria creada = categoriaService.crear(request.nombre(), request.descripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(creada));
    }

    /**
     * Actualiza una categoria existente.
     *
     * @param id      identificador de la categoria a actualizar
     * @param request nuevos datos de la categoria
     * @return 200 con la categoria actualizada, o 404 si no existe
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoria existente")
    public ResponseEntity<CategoriaResponse> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody CategoriaRequest request) {
        Categoria actualizada = categoriaService.actualizar(id, request.nombre(), request.descripcion());
        return ResponseEntity.ok(toResponse(actualizada));
    }

    /**
     * Elimina una categoria por su identificador.
     *
     * @param id identificador de la categoria a eliminar
     * @return 204 sin contenido, 404 si no existe, o 409 si tiene productos asociados
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoria")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }
}