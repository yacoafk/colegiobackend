# Sistema de Gestión Escolar — Backend

API REST para el Sistema de Control y Registro Escolar.  
**Stack:** Java 17 · Spring Boot 3.x · MySQL 8 · Spring Security · Maven

## Enlaces del Proyecto

| Recurso | Enlace |
| :--- | :--- |
| **Repositorio Backend** | [Enlace a tu GitHub] |
| **Repositorio Frontend** | [Enlace a tu GitHub de React] |
| **Documentación API (Swagger)** | http://localhost:8080/swagger-ui/index.html |

---

## Tabla de Contenidos
1. [Descripción General](#1-descripción-general)
2. [Stack Tecnológico](#2-stack-tecnológico)
3. [Estructura del Proyecto (Arquitectura)](#3-estructura-del-proyecto-arquitectura)
4. [Estructura del Paquete Java](#4-estructura-del-paquete-java)
5. [Endpoints de la API](#5-endpoints-de-la-api)
6. [Manejo de Errores](#6-manejo-de-errores)
7. [Cómo Ejecutar el Proyecto](#7-cómo-ejecutar-el-proyecto)

---

## 1. Descripción General
Este backend es una API REST modular desarrollada para gestionar las operaciones administrativas del colegio. Provee servicios desacoplados para el manejo de sedes institucionales, personal administrativo, asignación de roles y control de accesos de manera segura.

**Características Principales:**
* **Gestión Institucional:** CRUD completo de Sedes y Establecimientos.
* **Control de Personal:** Registro de empleados, validación de documentos (DNI) y asignación de roles dinámicos (Administrador, Docente).
* **Seguridad:** Arquitectura abierta para desarrollo con políticas CORS centralizadas y encriptación de credenciales.

---

## 2. Stack Tecnológico

| Herramienta | Versión | Uso |
| :--- | :--- | :--- |
| **Java** | 17 | Lenguaje de programación base |
| **Spring Boot** | 3.2.x / 3.3.x | Framework principal de la aplicación |
| **Spring Security** | 6.x | Configuración de accesos y políticas CORS |
| **Spring Data JPA** | — | Abstracción de persistencia de datos |
| **MySQL** | 8.x | Base de datos relacional |
| **Maven** | 3.9+ | Gestor de dependencias y ciclo de vida del build |

---

## 3. Estructura del Proyecto (Arquitectura)
El proyecto implementa una arquitectura en capas tradicional, asegurando un bajo acoplamiento mediante el uso de interfaces de servicio y objetos de transferencia de datos (DTOs):

* **Capa de Presentación (Controller):** Expone las rutas REST y maneja las peticiones/respuestas HTTP.
* **Capa de Negocio (Service / ServiceImpl):** Orquesta la lógica del sistema mediante interfaces desacopladas de su implementación.
* **Capa de Transferencia (DTO):** Objetos planos utilizados para validar y mapear los cuerpos de las peticiones (`Request`).
* **Capa de Persistencia (DAO / Repository):** Gestiona las consultas y transacciones directas con la base de datos MySQL mediante Spring Data JPA.

---

## 4. Estructura del Paquete Java

```text
com.colegio.backend/
│
├── config/                  # Seguridad, CORS y Beans globales
│   └── SecurityConfig.java
│
├── controller/              # Controladores REST (Entrypoints de la API)
│   ├── SedeController.java
│   └── PersonalController.java
│
├── dto/                     # Objetos de transferencia de datos (Peticiones)
│   └── SedeRequest.java
│
├── model/                   # Entidades del Modelo de Dominio (JPA)
│   ├── Sede.java
│   └── Personal.java
│
├── dao/                     # Repositorios / Data Access Objects (Interfaces JPA)
│   ├── SedeRepository.java
│   └── PersonalRepository.java
│
└── service/                 # Capa de Lógica de Negocio
    ├── SedeService.java     # Interfaz de contrato
    └── impl/
        └── SedeServiceImpl.java # Implementación real del negocio