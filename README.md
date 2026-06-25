# Sistema de Gestion de Inventario Inteligente

Microservicio REST para la gestion de inventario de un deposito, desarrollado con
Spring Boot 3.5 y Java 21. Implementa repositorios genericos en memoria, el patron
Strategy para alertas de stock, y documentacion completa via Swagger/OpenAPI.

## Tecnologias

- Java 21
- Spring Boot 3.5
- Spring Web + Jakarta Validation
- springdoc-openapi (Swagger UI)
- JUnit 5
- Maven

## Requisitos previos

- JDK 21 o superior
- Maven 3.6 o superior

## Como ejecutar el proyecto

Clonar el repositorio y posicionarse en la carpeta del proyecto:

```bash
git clone <url-del-repositorio>
cd tp5-inventario
```

Compilar y correr los tests:

```bash
mvn clean test
```

Levantar la aplicacion:

```bash
mvn spring-boot:run
```

La aplicacion queda disponible en `http://localhost:8081`.

## Documentacion interactiva (Swagger)

Con la aplicacion corriendo, la documentacion de todos los endpoints esta disponible en:
http://localhost:8081/swagger-ui.html

## Estructura del proyecto

src/main/java/com/inventory/smart/

├── model/         Entidades de dominio (Producto, Categoria, MovimientoInventario)

├── repository/    Repositorios genericos e implementaciones en memoria

├── service/       Logica de negocio y patron Strategy para alertas

├── controller/     Controladores REST

├── dto/           Records de entrada/salida (Request/Response)

├── exception/     Excepciones de dominio y manejador global

└── config/        Configuracion de umbrales de stock

## Endpoints principales

| Metodo | Endpoint                          | Descripcion                              |
|--------|------------------------------------|--------------------------------------------|
| GET    | `/api/productos`                  | Listar productos (con filtros opcionales) |
| POST   | `/api/productos`                  | Crear un producto                         |
| GET    | `/api/categorias`                 | Listar categorias                         |
| POST   | `/api/movimientos`                | Registrar entrada o salida de stock       |
| GET    | `/api/alertas`                    | Consultar productos con alerta de stock   |
| GET    | `/api/admin/performance-report`   | Ejecutar benchmark del repositorio        |

## Reglas de negocio principales

- No se puede registrar una SALIDA de stock mayor a la cantidad disponible (409 Conflict).
- No se puede eliminar una categoria que tenga productos asociados (409 Conflict).
- Las alertas de stock (BAJO/CRITICO) se calculan segun umbrales configurables en `application.yml`.

## Informe de rendimiento

Ver [`PERFORMANCE.md`](./PERFORMANCE.md) para el analisis de complejidad Big O
teorica versus mediciones reales sobre volumenes de 1.000, 10.000 y 100.000 registros.

## Autores

- Ceballos Palacios Santiago EISI800
- Vargas Lanzone Adrian EISI785
