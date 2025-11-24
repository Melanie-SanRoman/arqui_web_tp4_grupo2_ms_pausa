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

Pendientes de agregar: 
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

## Colaboradores

> * Langenheim, Geronimo V.
> * Lopez, Micaela N.
> * San Roman, Emanuel.
> * San Roman, Melanie.
