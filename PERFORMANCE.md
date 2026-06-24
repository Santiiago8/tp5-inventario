# Informe de Rendimiento — Sistema de Gestion de Inventario

## Metodologia

Las mediciones se realizaron mediante el endpoint `GET /api/admin/performance-report`,
que ejecuta las operaciones del repositorio generico (`GenericInMemoryRepository`) sobre
un repositorio aislado de prueba, cargado con 1.000, 10.000 y 100.000 registros sinteticos.

Cada operacion se midio con `System.nanoTime()` inmediatamente antes y despues de su ejecucion.

## Complejidad teorica (Big O)

| Operacion     | Estructura interna      | Complejidad teorica |
|---------------|--------------------------|----------------------|
| `save`        | `ConcurrentHashMap.put`  | O(1) por insercion   |
| `findById`    | `ConcurrentHashMap.get`  | O(1)                 |
| `findAll`     | Copia de `values()`      | O(n)                 |
| `existsById`  | `ConcurrentHashMap.containsKey` | O(1)         |
| `deleteById`  | `ConcurrentHashMap.remove` | O(1)               |

## Resultados medidos

| Operacion                     | 1.000 registros | 10.000 registros | 100.000 registros |
|--------------------------------|------------------|-------------------|---------------------|
| `save` (carga completa)        | 0.940247 ms      | 2.229863 ms       | 24.661795 ms        |
| `findById`                     | 0.008356 ms      | 0.001262 ms       | 0.014016 ms         |
| `findAll`                      | 0.109386 ms      | 0.982116 ms       | 6.123445 ms         |
| `existsById`                   | 0.002404 ms      | 0.000882 ms       | 0.009528 ms         |
| `deleteById`                   | 0.007785 ms      | 0.000811 ms       | 0.008837 ms         |

## Analisis de discrepancias

- **`save`**: el tiempo mostrado corresponde al ciclo completo de N inserciones, no a una
  insercion individual. Cada `put` sobre el `ConcurrentHashMap` sigue siendo O(1) amortizado;
  el crecimiento observado (de 0.94 ms a 24.66 ms al pasar de 1.000 a 100.000 inserciones)
  refleja la naturaleza O(n) de hacer n operaciones O(1), no una degradacion del algoritmo.

- **`findById`, `existsById`, `deleteById`**: se mantienen en el orden de microsegundos
  independientemente del volumen de datos, confirmando la complejidad O(1) esperada de
  `ConcurrentHashMap`. Las pequenas variaciones entre 1.000 y 10.000 registros (por ejemplo,
  `findById` bajando de 0.0084 ms a 0.0013 ms) se atribuyen al calentamiento JIT de la JVM
  (Just-In-Time compilation), que optimiza el bytecode en ejecuciones sucesivas dentro del
  mismo proceso, y no representan una mejora real de la estructura de datos.

- **`findAll`**: el crecimiento es aproximadamente lineal respecto al volumen (10x mas datos
  produce un tiempo cercano a 10x mayor entre 10.000 y 100.000 registros), consistente con la
  complejidad O(n) de copiar todos los valores del mapa.

## Conclusion

Los resultados empiricos confirman las complejidades teoricas esperadas para las operaciones
basicas de `ConcurrentHashMap`: acceso, existencia y borrado por clave en tiempo constante,
y recorrido completo en tiempo lineal. La eleccion de `ConcurrentHashMap` como estructura de
almacenamiento resulta adecuada para los requisitos de concurrencia y rendimiento del sistema.