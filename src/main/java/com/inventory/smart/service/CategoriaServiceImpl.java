package com.inventory.smart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inventory.smart.exception.BusinessRuleException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.ProductoRepository;

/**
 * Implementacion del servicio de gestion de categorias.
 *
 * @author Grupo
 * @since 1.0
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    /**
     * Crea el servicio inyectando sus dependencias por constructor.
     *
     * @param categoriaRepository repositorio de categorias
     * @param productoRepository  repositorio de productos, usado para validar integridad referencial
     */
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository,
                                ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    /** {@inheritDoc} */
    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe categoria con id: " + id));
    }

    /** {@inheritDoc} */
    @Override
    public Categoria crear(String nombre, String descripcion) {
        Categoria categoria = new Categoria(null, nombre, descripcion);
        return categoriaRepository.save(categoria);
    }

    /** {@inheritDoc} */
    @Override
    public Categoria actualizar(Long id, String nombre, String descripcion) {
        Categoria categoria = findById(id);
        if (nombre != null) {
            categoria.setNombre(nombre);
        }
        if (descripcion != null) {
            categoria.setDescripcion(descripcion);
        }
        return categoriaRepository.save(categoria);
    }

    /** {@inheritDoc} */
    @Override
    public void eliminar(Long id) {
        findById(id);
        boolean tieneProductos = !productoRepository.findByCategoria(id).isEmpty();
        if (tieneProductos) {
            throw new BusinessRuleException(
                    "No se puede eliminar la categoria " + id + " porque tiene productos asociados");
        }
        categoriaRepository.deleteById(id);
    }
}