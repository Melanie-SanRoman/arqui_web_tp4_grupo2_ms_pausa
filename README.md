# Arquitecturas Web - Microservicio -> `pausa_service`

## Descripcion

Microservicio encargado de gestionar las pausas realizadas durante un viaje en monopatín.
Permite registrar pausas, listar las pausas asociadas a un viaje y calcular los minutos totales de pausa.

## Arquitectura y responsabilidades

Este microservicio forma parte de un sistema distribuido compuesto por:
* `viaje_service` -> Consulta pausas para calcular el costo final de un viaje.
* `tarifa_service` -> Usa la información de pausa de manera indirecta a través de viaje.

Responsabilidades de `pausa_service`:
* Registrar una pausa asociada a un viaje.
* Obtener todas las pausas vinculadas a un viaje.
* Calcular los minutos totales de pausa.
* Exponer la informacion a otros microservcios mediante REST.

## Entidad principal 

**Pausa**
| Campo   | Tipo      | Descripción                                  |
| ------- | --------- | -------------------------------------------- |
| id      | Long      | Identificador único                          |
| inicio  | LocalDate | Momento en que inicia la pausa               |
| fin     | LocalDate | Momento en que finaliza la pausa             |
| viajeId | Long      | Identificador del viaje asociado (FK lógica) |

## Endoints disponibles

### Documentacion Swagger / OpenAPI
- Este microservicio expone su documentación interactiva en:
> [Swagger UI](http://localhost:8081/swagger-ui/index.html)

---

* GET `/api/pausas/viaje/{viajeId}/total-minutos`
  * Obtener minutos totales de pausa.
  * Devuelve un `PausaTotalDTO.java` con la suma de minutos en `Double`.

```json
{
  "viajeId": 4,
  "minutosTotales": 10.2
}
```

* GET `/api/pausas/viaje/{viajeId}/pausas`
  * Obtener pausas por viaje.
  * Devuelve una lista de `PausaResponseDTO.java`.
  
```json
{
  "id": 7,
  "inicio": ,
  "fin": ,
  "viajeId": 4
}
```

* POST `/api/pausas/{viajeId}`
  * Registrar una pausa.
  * Crea una nueva pausa asociada a un viaje.

## Logica de negocio

1. Cada pausa contiene fecha/hora de inicio y fin.
2. Todas las pausas pertenecen a un viaje (relacion por ID, no fisica).
3. Para calcular minutos totales, el servicio:
   * Obtiene todas las pausas del viaje.
   * Calcula los minutos de cada pausa.
   * Suma todas las duraciones y retorna el total.
Este valor es consumido por `viaje_Service` para determinar la tarifa a aplicar.
  
## Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* Swagger
* Autenticacion JWT

## Ejecucion 

Requisistos previos:
* Java 21+
* Maven
* MySQL en ejecucion

Ajustar credenciales en `application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/viaje_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

## Diagramas del microservicio
Endpoint y dependencias internas

``` mermaid
flowchart LR
%% Estilos globales
%% ----------------------------------------
classDef service fill:#e8f1ff,stroke:#3a6ea5,stroke-width:2px,color:#0b2545,rx:10,ry:10;
classDef db fill:#fff4d6,stroke:#b68b00,stroke-width:2px,color:#4e3b00,rx:10,ry:10;
classDef op fill:#e2ffe9,stroke:#41a35a,stroke-width:2px,color:#1a4e26,rx:10,ry:10;
    subgraph Pausa_Service ["pausa_service"]

        B1[POST /pausas/iniciar]:::service --> B2[(Pausa)]:::db
        B3[POST /pausas/finalizar]:::service --> B2
        B4[GET /pausas/viaje/id]:::service -->|Devuelve lista| B2
        B5[GET /pausas/viaje/id/total-minutos]:::service -->|Minutos totales| B2

    end
```
Este diagrama muestra cómo pausa_service se encarga de:

* Registrar pausas.
* Finalizarlas.
* Calcular minutos totales.
* Informar al viaje_service.

---

Diagrama C4

``` mermaid
%% Estilos globales
graph TD
classDef ms fill:#e3f2fd,stroke:#64b5f6,stroke-width:2px,color:#0d47a1,rx:10px,ry:10px;
classDef db fill:#fff3e0,stroke:#ffb74d,stroke-width:2px,color:#e65100,rx:10px,ry:10px;
classDef comp fill:#e8f5e9,stroke:#81c784,stroke-width:2px,color:#1b5e20,rx:10px,ry:10px;
classDef persona fill:#fce4ec,stroke:#f06292,stroke-width:2px,color:#880e4f,rx:10px,ry:10px;
classDef relation stroke-dasharray: 5 5;
subgraph pausa_service
    controller([PausaController]):::comp
    service([PausaService]):::comp
    repo[(PausaRepository)]:::db
    entity[[Pausa]]:::comp
end

controller --> service
service --> repo
service --> entity
```

## Colaboradores

> * Langenheim, Geronimo V.
> * Lopez, Micaela N.
> * San Roman, Emanuel.
> * San Roman, Melanie.
