
# Documentación del Proyecto: Aplicación de Recetas

## Descripción General
Este proyecto es una aplicación web para la gestión y consulta de recetas culinarias. Incluye funcionalidades de búsqueda, autenticación de usuarios y vistas dinámicas utilizando Spring Boot y Thymeleaf.

---

## Estructura del Proyecto

### Paquetes y Clases Principales
- **`com.demo.recetas.config`**
  - `SecurityConfig`: Configuración de seguridad para la aplicación. Define rutas públicas, página de login personalizada, y un servicio de usuarios en memoria.

- **`com.demo.recetas.controller`**
  - `BuscarController`: Maneja la funcionalidad de búsqueda de recetas.
  - `HomeController`: Controla la página de inicio y muestra recetas recientes.
  - `LoginController`: Gestiona la visualización de la página de inicio de sesión.
  - `RecetaController`: Muestra los detalles de una receta específica.

- **`com.demo.recetas.model`**
  - `Receta`: Clase que representa las recetas con atributos como nombre, tipo de cocina, ingredientes, etc.
  - `Usuario`: Clase que representa a los usuarios del sistema.

- **`com.demo.recetas.repository`**
  - `RecetaRepository`: Interfaz para interactuar con la base de datos de recetas. Extiende `JpaRepository` e incluye un método personalizado para buscar recetas por nombre.

---

## Funcionalidades Principales
1. **Autenticación y Autorización**:
   - Configurada con Spring Security.
   - Usuarios en memoria con contraseñas codificadas.
   - Páginas públicas: `/`, `/login`, `/buscar`, `/css/**`, `/images/**`.
   - Otras rutas requieren autenticación.

2. **Gestión de Recetas**:
   - Búsqueda de recetas por nombre (case-insensitive).
   - Visualización de detalles de una receta (nombre, tipo de cocina, ingredientes, dificultad, tiempo de cocción, e instrucciones).

3. **Plantillas Thymeleaf**:
   - `index.html`: Página de inicio con recetas recientes y populares.
   - `buscar.html`: Página de búsqueda de recetas.
   - `detalleReceta.html`: Detalles de una receta específica.
   - `login.html`: Página de inicio de sesión.

---

## Dependencias Principales
Listado de dependencias incluidas en el archivo `pom.xml`:

- **Spring Boot Starter**:
  - `spring-boot-starter-data-jpa`: Gestión de datos y persistencia.
  - `spring-boot-starter-security`: Seguridad y autenticación.
  - `spring-boot-starter-thymeleaf`: Integración con Thymeleaf.
  - `spring-boot-starter-web`: Desarrollo de aplicaciones web.

- **Thymeleaf Extras**:
  - `thymeleaf-extras-springsecurity6`: Extensiones de seguridad para Thymeleaf.

- **Pruebas**:
  - `spring-boot-starter-test`: Herramientas para pruebas unitarias.
  - `spring-security-test`: Utilidades para probar seguridad.

- **Conector Oracle JDBC**:
  - `ojdbc8`: Controlador para conectarse a Oracle Database.

---

## Estructura de Carpetas
```
recetas
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.demo.recetas
│   │   │       ├── config          # Configuraciones de seguridad
│   │   │       │   └── SecurityConfig.java
│   │   │       ├── controller      # Controladores para manejar las rutas
│   │   │       │   ├── BuscarController.java
│   │   │       │   ├── HomeController.java
│   │   │       │   ├── LoginController.java
│   │   │       │   └── RecetaController.java
│   │   │       ├── model           # Modelos de datos
│   │   │       │   ├── Receta.java
│   │   │       │   └── Usuario.java
│   │   │       ├── repository      # Repositorios de datos
│   │   │       │   └── RecetaRepository.java
│   ├── resources
│   │   ├── templates               # Plantillas Thymeleaf
│   │   │   ├── buscar.html
│   │   │   ├── detalleReceta.html
│   │   │   ├── index.html
│   │   │   └── login.html
│   │   ├── static                  # Recursos estáticos (CSS, imágenes)
│   └── test
├── pom.xml                         # Archivo de configuración de Maven
```

---

## Ejecución del Proyecto
1. Asegúrate de tener configurado un entorno con:
   - **Java 17**
   - **Maven** instalado.
   - **Base de datos Oracle** (si planeas usarla).
2. Instala las dependencias:
   ```bash
   mvn clean install
   ```
3. Ejecuta el proyecto:
   ```bash
   mvn spring-boot:run
   ```
4. Accede a la aplicación en: [http://localhost:8080](http://localhost:8080)

---

## Notas Adicionales
- **Seguridad**:
  - Los usuarios preconfigurados están definidos en `SecurityConfig`:
    - Usuario: `usuario1`, Contraseña: `password1`
    - Usuario: `usuario2`, Contraseña: `password2`
    - Usuario: `usuario3`, Contraseña: `password3`

- **Persistencia**:
  - El proyecto utiliza `JpaRepository` y mapeos JPA para interactuar con la base de datos.
  - Asegúrate de configurar correctamente la conexión a Oracle en `application.properties`.

---

© 2024 Aplicación de Recetas.
