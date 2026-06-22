# POS System — Backend (API REST)

API REST de un sistema de **Punto de Venta (POS)** desarrollada con **Spring Boot**. Gestiona autenticación con roles, productos e inventario, categorías, clientes, ventas con descuento automático de stock y métricas para el dashboard.

> Frontend del proyecto: [POS-System-frontend](https://github.com/jeanfrancochamorro2006-jpg/POS-System-frontend)

---

## 🚀 Tecnologías

- **Java 17+** / **Spring Boot 4**
- **Spring Security + JWT** (autenticación con roles)
- **Spring Data JPA / Hibernate**
- **MySQL**
- **Maven**
- Validación con Jakarta Bean Validation

---

## ✨ Funcionalidades

- 🔐 **Autenticación JWT** con roles **ADMIN** y **CAJERO**
- 👥 **Gestión de usuarios** (solo administrador), contraseñas encriptadas con BCrypt
- 🏷️ **Categorías** de productos (CRUD)
- 📦 **Productos / Inventario** con control de stock, precio y estado
- 🧑 **Clientes**
- 🛒 **Ventas** transaccionales: validan stock, **descuentan inventario** y calculan totales
- 🧾 **Historial** de ventas con detalle
- 📊 **Dashboard**: ventas del día, ingreso semanal, productos con bajo stock e ingresos por método de pago

---

## 📋 Requisitos previos

- JDK 17 o superior
- MySQL en ejecución (por defecto en `localhost:3306`)
- Maven (o usar el wrapper incluido `./mvnw`)

---

## ⚙️ Configuración

1. Crear la base de datos (las tablas se generan solas con Hibernate):

   ```sql
   CREATE DATABASE db_formularios CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. Ajustar credenciales en [`src/main/resources/application.properties`](src/main/resources/application.properties) si es necesario:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/db_formularios
   spring.datasource.username=root
   spring.datasource.password=
   ```

---

## ▶️ Ejecución

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / Mac
./mvnw spring-boot:run
```

La API queda disponible en **http://localhost:8080**.

Al iniciar se crean automáticamente datos de ejemplo y los usuarios por defecto:

| Usuario  | Contraseña  | Rol     |
|----------|-------------|---------|
| `admin`  | `admin123`  | ADMIN   |
| `cajero` | `cajero123` | CAJERO  |

---

## 📡 Endpoints principales

| Método | Ruta                       | Descripción                       | Acceso |
|--------|----------------------------|-----------------------------------|--------|
| POST   | `/api/auth/login`          | Iniciar sesión (devuelve JWT)     | Público |
| GET    | `/api/auth/me`             | Datos del usuario autenticado     | Autenticado |
| GET    | `/api/productos`           | Listar productos                  | Autenticado |
| POST   | `/api/productos`           | Crear producto                    | ADMIN |
| GET    | `/api/categorias`          | Listar categorías                 | Autenticado |
| POST   | `/api/categorias`          | Crear categoría                   | ADMIN |
| GET    | `/api/clientes`            | Listar clientes                   | Autenticado |
| POST   | `/api/ventas`              | Registrar venta                   | Autenticado |
| GET    | `/api/ventas`              | Historial de ventas               | Autenticado |
| GET    | `/api/dashboard/resumen`   | Métricas del dashboard            | Autenticado |
| GET    | `/api/usuarios`            | Gestión de usuarios               | ADMIN |

> Todas las rutas (excepto `/api/auth/**`) requieren la cabecera `Authorization: Bearer <token>`.

---

## 🗂️ Estructura

```
src/main/java/formulario_api/
├── config/         # Seguridad, CORS, manejo de errores, datos iniciales
├── controller/     # Endpoints REST
├── dto/            # Objetos de petición/respuesta
├── entity/         # Entidades JPA
├── repository/     # Repositorios Spring Data
├── security/       # JWT (servicio, filtro, UserDetails)
└── service/        # Lógica de negocio
```

---

## 📝 Licencia

Proyecto académico de desarrollo web.
