# 🚦 Infracciones Tránsito API - TDD Implementation

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-brightgreen?style=for-the-badge&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![JUnit 5](https://img.shields.io/badge/JUnit-5-red?style=for-the-badge&logo=junit5)
![GitHub Actions](https://img.shields.io/badge/CI-GitHub_Actions-black?style=for-the-badge&logo=github-actions)

Proyecto de gestión de infracciones de tránsito desarrollado bajo la metodología **TDD (Test Driven Development)** siguiendo el ciclo *Red-Green-Refactor*.

## 🚀 Características y Retos Resueltos

### 1. Bloqueo Automático de Infractores
Implementación de lógica para bloquear infractores que acumulan **3 o más multas en estado VENCIDA**.
- **Prueba realizada**: Validación de que un infractor con solo 2 multas vencidas permanece activo.

### 2. Gestión de Estados de Multas
Automatización del cambio de estado de multas de `PENDIENTE` a `VENCIDA` basándose en la fecha de vencimiento comparada con la fecha actual.

### 3. Procesamiento de Pagos con Reglas de Negocio
Sistema de cobro inteligente que aplica:
- **Descuento del 20%**: Si el pago se realiza dentro de los primeros 5 días.
- **Recargo del 15%**: Si la multa ya está vencida.
- **Validaciones**: Protección contra pagos duplicados (`PagoYaRealizadoException`).

### 4. Verificación Avanzada con ArgumentCaptor
Uso de técnicas de Mockito para capturar y validar objetos de persistencia durante el flujo de pago, asegurando la integridad de los datos guardados en el repositorio.

---

## 🛠️ Tecnologías Utilizadas

- **Framework**: Spring Boot 4.0.5
- **Persistencia**: Spring Data JPA / Hibernate
- **Base de Datos**: MySQL
- **Testing**: JUnit 5, Mockito, ArgumentCaptor
- **Configuración**: Dotenv para variables de entorno
- **Documentación**: Swagger / OpenAPI 3

---

## ⚙️ Integración Continua (CI)

El proyecto cuenta con un **Pipeline de GitHub Actions** que:
1. Levanta un contenedor con **MySQL 8.0**.
2. Configura el entorno de pruebas automáticamente.
3. Ejecuta el ciclo completo de pruebas con **Maven**.
4. Garantiza que cualquier cambio mantenga la estabilidad del sistema.

---

## 🏁 Cómo Ejecutar

1. Clona el repositorio.
2. Configura tu archivo `.env` basándote en las variables:
   ```env
   BD_URL=jdbc:mysql://localhost:3306/infracciones_db
   BD_USERNAME=tu_usuario
   BD_PASSWORD=tu_password
   ```
3. Ejecuta las pruebas:
   ```bash
   ./mvnw test
   ```
4. Inicia la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

---
*Desarrollado con ❤️ para demostrar habilidades técnicas en Testing y Backend Development.*
