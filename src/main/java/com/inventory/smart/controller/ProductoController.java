package com.inventory.smart.controller;

import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.dto.ProductoRequest;
import com.inventory.smart.dto.ProductoResponse;
import com.inventory.smart.model.Producto;
import com.inventory.smart.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestion de productos del inventario.
 *
 * @author Grupo
 * @since 1.0
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones sobre productos del inventario")
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Crea el controlador inyectando el servicio de productos.
     *
     * @param productoService servicio de logica de negocio de productos
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Lista productos, con filtros opcionales combinables.
     *
     * @param categoriaId filtro por categoria
     * @param precioMin   filtro de precio minimo
     * @param precioMax   filtro de precio maximo
     * @param enStock     si es true, solo productos con stock mayor a 0
     * @return 200 con la lista de productos filtrados
     */
    @GetMapping
    @Operation(summary = "Listar productos con filtros opcionales")
    public ResponseEntity<List<ProductoResponse>> listar(
            @Parameter(description = "Filtrar por id de categoria")
            @RequestParam(required = false) Long categoriaId,
            @Parameter(description = "Precio minimo")
            @RequestParam(required = false) Double precioMin,
            @Parameter(description = "Precio maximo")
            @RequestParam(required = false) Double precioMax,
            @Parameter(description = "Solo productos con stock disponible")
            @RequestParam(required = false) Boolean enStock) {

        List<ProductoResponse> response = productoService.findAll(categoriaId, precioMin, precioMax, enStock)
                .stream().map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Lista productos ordenados por un campo especifico.
     *
     * @param campo campo de ordenamiento: nombre, precio o stock
     * @param orden direccion del orden: asc o desc
     * @return 200 con la lista ordenada
     */
    @GetMapping("/ordenados")
    @Operation(summary = "Listar productos ordenados por nombre, precio o stock")
    public ResponseEntity<List<ProductoResponse>> listarOrdenados(
            @RequestParam(defaultValue = "nombre") String campo,
            @RequestParam(defaultValue = "asc") String orden) {

        List<ProductoResponse> response = productoService.listarOrdenados(campo, orden)
                .stream().map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Busca productos cuyo nombre contenga el texto dado.
     *
     * @param texto texto a buscar
     * @return 200 con la lista de productos que coinciden
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre")
    public ResponseEntity<List<ProductoResponse>> buscar(@RequestParam String texto) {
        List<ProductoResponse> response = productoService.buscarPorNombre(texto)
                .stream().map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un producto por su identificador.
     *
     * @param id identificador del producto
     * @return 200 con el producto encontrado, o 404 si no existe
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por id")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(productoService.findById(id)));
    }

    /**
     * Crea un nuevo producto.
     *
     * @param request datos del producto a crear
     * @return 201 con el producto creado, o 404 si la categoria no existe
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        Producto creado = productoService.crear(
                request.nombre(), request.descripcion(), request.precio(),
                request.stockInicial(), request.categoriaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(creado));
    }

    /**
     * Actualiza un producto existente. Los campos no enviados no se modifican.
     *
     * @param id      identificador del producto a actualizar
     * @param request nuevos datos del producto
     * @return 200 con el producto actualizado, o 404 si no existe
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody ProductoRequest request) {
        Producto actualizado = productoService.actualizar(
                id, request.nombre(), request.descripcion(), request.precio(), request.categoriaId());
        return ResponseEntity.ok(toResponse(actualizado));
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id identificador del producto a eliminar
     * @return 204 sin contenido, o 404 si no existe
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private ProductoResponse toResponse(Producto producto) {
        CategoriaResponse categoriaResponse = new CategoriaResponse(
                producto.getCategoria().getId(),
                producto.getCategoria().getNombre(),
                producto.getCategoria().getDescripcion());

        return new ProductoResponse(
                producto.getId(), producto.getNombre(), producto.getDescripcion(),
                producto.getPrecio(), producto.getStock(), categoriaResponse);
    }
}