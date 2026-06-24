package com.inventory.smart.service;

import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Implementacion del servicio de gestion de productos.
 *
 * @author Grupo
 * @since 1.0
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * Crea el servicio inyectando sus dependencias por constructor.
     *
     * @param productoRepository  repositorio de productos
     * @param categoriaRepository repositorio de categorias, usado para validar la categoria asociada
     */
    public ProductoServiceImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /** {@inheritDoc} */
    @Override
    public List<Producto> findAll(Long categoriaId, Double precioMin, Double precioMax, Boolean enStock) {
        return productoRepository.findAll().stream()
                .filter(p -> categoriaId == null || p.getCategoria().getId().equals(categoriaId))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .filter(p -> enStock == null || !enStock || p.getStock() > 0)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe producto con id: " + id));
    }

    /** {@inheritDoc} */
    @Override
    public Producto crear(String nombre, String descripcion, double precio, int stockInicial, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe categoria con id: " + categoriaId));
        Producto producto = new Producto(null, nombre, descripcion, precio, stockInicial, categoria);
        return productoRepository.save(producto);
    }

    /** {@inheritDoc} */
    @Override
    public Producto actualizar(Long id, String nombre, String descripcion, Double precio, Long categoriaId) {
        Producto producto = findById(id);

        if (nombre != null) {
            producto.setNombre(nombre);
        }
        if (descripcion != null) {
            producto.setDescripcion(descripcion);
        }
        if (precio != null) {
            producto.setPrecio(precio);
        }
        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new ResourceNotFoundException("No existe categoria con id: " + categoriaId));
            producto.setCategoria(categoria);
        }

        return productoRepository.save(producto);
    }

    /** {@inheritDoc} */
    @Override
    public void eliminar(Long id) {
        findById(id);
        productoRepository.deleteById(id);
    }

    /** {@inheritDoc} */
    @Override
    public List<Producto> buscarPorNombre(String texto) {
        return productoRepository.buscarPorNombre(texto);
    }

    /** {@inheritDoc} */
    @Override
    public List<Producto> listarOrdenados(String campo, String orden) {
        Comparator<Producto> comparator = switch (campo.toLowerCase()) {
            case "precio" -> Comparator.comparingDouble(Producto::getPrecio);
            case "stock" -> Comparator.comparingInt(Producto::getStock);
            default -> Comparator.comparing(Producto::getNombre);
        };

        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }

        return productoRepository.findAll().stream()
                .sorted(comparator)
                .toList();
    }
}