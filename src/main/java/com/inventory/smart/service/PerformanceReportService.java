package com.inventory.smart.service;

import com.inventory.smart.dto.PerformanceResultado;
import com.inventory.smart.dto.PerformanceResultado.MedicionOperacion;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.GenericInMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que mide el tiempo de ejecucion de las operaciones basicas
 * del repositorio generico para distintos volumenes de datos.
 *
 * <p>Las mediciones se hacen sobre un repositorio aislado de prueba,
 * para no afectar los datos reales de la aplicacion.</p>
 *
 * @author Grupo
 * @since 1.0
 */
@Service
public class PerformanceReportService {

    private static final int[] VOLUMENES = {1_000, 10_000, 100_000};

    /**
     * Ejecuta las mediciones de rendimiento para los volumenes de
     * 1.000, 10.000 y 100.000 registros.
     *
     * @return lista de resultados, uno por cada volumen de datos
     */
    public List<PerformanceResultado> ejecutarMediciones() {
        List<PerformanceResultado> resultados = new ArrayList<>();
        for (int volumen : VOLUMENES) {
            resultados.add(medirVolumen(volumen));
        }
        return resultados;
    }

    private PerformanceResultado medirVolumen(int cantidad) {
        TestRepository repo = new TestRepository();
        Categoria categoriaPrueba = new Categoria(1L, "Prueba", "Categoria de prueba");

        List<MedicionOperacion> mediciones = new ArrayList<>();

        // Medicion de save() (insercion)
        long inicioSave = System.nanoTime();
        for (int i = 0; i < cantidad; i++) {
            repo.save(new Producto(null, "Producto " + i, "desc", 10.0, 100, categoriaPrueba));
        }
        long finSave = System.nanoTime();
        mediciones.add(crearMedicion("save (insercion x" + cantidad + ")", finSave - inicioSave));

        // Medicion de findById() (busqueda puntual, ultimo elemento insertado)
        long idBusqueda = cantidad; // el ultimo id generado
        long inicioFindById = System.nanoTime();
        repo.findById(idBusqueda);
        long finFindById = System.nanoTime();
        mediciones.add(crearMedicion("findById", finFindById - inicioFindById));

        // Medicion de findAll() (recorrido completo)
        long inicioFindAll = System.nanoTime();
        repo.findAll();
        long finFindAll = System.nanoTime();
        mediciones.add(crearMedicion("findAll", finFindAll - inicioFindAll));

        // Medicion de existsById()
        long inicioExists = System.nanoTime();
        repo.existsById(idBusqueda);
        long finExists = System.nanoTime();
        mediciones.add(crearMedicion("existsById", finExists - inicioExists));

        // Medicion de deleteById()
        long inicioDelete = System.nanoTime();
        repo.deleteById(idBusqueda);
        long finDelete = System.nanoTime();
        mediciones.add(crearMedicion("deleteById", finDelete - inicioDelete));

        return new PerformanceResultado(cantidad, mediciones);
    }

    private MedicionOperacion crearMedicion(String operacion, long tiempoNanos) {
        double tiempoMilis = tiempoNanos / 1_000_000.0;
        return new MedicionOperacion(operacion, tiempoNanos, tiempoMilis);
    }

    /**
     * Repositorio interno usado exclusivamente para las mediciones de rendimiento,
     * aislado de los datos reales de la aplicacion.
     */
    private static class TestRepository extends GenericInMemoryRepository<Producto, Long> {
        TestRepository() {
            super(Producto::getId, Producto::setId, Long::valueOf);
        }
    }
}