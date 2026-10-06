# SpeedFast

## Desarrollo Orientado a Objetos II

**Autor:** Maximiliano Villalobos  
**Institución:** Duoc UC  
**Actividad:** Integración de lógica de negocio y gestión de datos (CRUD con JDBC y Patrón DAO)  
**Semana:** 8

---

## 📋 Descripción

Proyecto desarrollado para la asignatura **Desarrollo Orientado a Objetos II**, correspondiente a la actividad sumativa de la semana 8.

El proyecto completa el ciclo funcional de la aplicación de escritorio para la empresa **SpeedFast**. El sistema integra una interfaz gráfica de usuario (GUI) construida con **Java Swing** y una base de datos relacional **MySQL** conectada a través de **JDBC**.

La solución aplica el patrón de diseño **DAO (Data Access Object)** y una arquitectura en capas para separar la vista, la lógica de negocio y el acceso a los datos, permitiendo realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) de manera segura y persistente sobre repartidores, pedidos y entregas.

---

## 🗂️ Arquitectura y Clases del Proyecto

El proyecto está estructurado en cuatro paquetes principales para garantizar un código modular y mantenible:

### 1. Paquete `config`
- **`ConexionDB`**: Gestiona la conexión a la base de datos MySQL de forma centralizada utilizando el driver JDBC.

### 2. Paquete `model` (Entidades)
Representan los datos del negocio y mapean las tablas de la base de datos:
- **`Repartidor`**: Almacena el ID y nombre del trabajador.
- **`Pedido`**: Contiene la dirección de entrega y utiliza los enums `TipoPedido` (COMIDA, ENCOMIENDA, EXPRESS) y `EstadoPedido` (PENDIENTE, EN_REPARTO, ENTREGADO).
- **`Entrega`**: Entidad asociativa que vincula un Repartidor con un Pedido, registrando la fecha y hora de asignación.

### 3. Paquete `dao` (Data Access Object)
Encapsulan toda la lógica de acceso a datos aislando las consultas SQL del resto del sistema:
- **`RepartidorDAO`**, **`PedidoDAO`**, **`EntregaDAO`**: Implementan los métodos CRUD utilizando `PreparedStatement` para prevenir inyecciones SQL y `ResultSet` para leer la información.

### 4. Paquete `ui` (Interfaz Gráfica)
Paneles visuales desarrollados con Java Swing que interactúan con los DAO:
- **`GestionRepartidoresPanel`**, **`GestionPedidosPanel`**, **`GestionEntregasPanel`**: Formularios y tablas (`JTable`) para gestionar la información. Utilizan `JComboBox` para manejar los Enums y las relaciones entre entidades.
- **`VentanaPrincipal`**: Contenedor principal (`JFrame`) que integra todos los paneles mediante un sistema de pestañas (`JTabbedPane`).

---

## 🔄 Operaciones CRUD y Flujo de Datos

El sistema permite gestionar la persistencia en tiempo real:

1. **Crear (Create):** Los formularios capturan los datos, aplican validaciones básicas y los envían a los métodos `agregar...()` de los DAO, ejecutando un `INSERT INTO` en MySQL.
2. **Leer (Read):** Las tablas (`JTable`) se pueblan dinámicamente utilizando modelos por defecto (`DefaultTableModel`) que consumen listas generadas por los métodos `listar...()` (consultas `SELECT`).
3. **Eliminar (Delete):** Al seleccionar una fila en las tablas, el usuario puede confirmar la eliminación del registro mediante `JOptionPane`, lo que dispara un `DELETE` en la base de datos según el ID interno.

---

## 🗄️ Base de Datos

El sistema utiliza MySQL con la base de datos `speedfast_db`. La persistencia se organiza en las siguientes tablas relacionales:

- **`repartidores`**: `id` (PK), `nombre`.
- **`pedidos`**: `id` (PK), `direccion`, `tipo` (ENUM), `estado` (ENUM).
- **`entregas`**: `id` (PK), `id_pedido` (FK), `id_repartidor` (FK), `fecha`, `hora`.

---

## 🛠️ Herramientas y Conceptos Utilizados

- Java
- IntelliJ IDEA
- Java Swing (`JFrame`, `JPanel`, `JTable`, `JComboBox`, `JTabbedPane`)
- JDBC (Java Database Connectivity)
- Base de Datos Relacional (MySQL)
- Patrón de Diseño DAO (Data Access Object)
- Arquitectura en Capas (MVC simplificado)
- `PreparedStatement` y `ResultSet`
- Programación Orientada a Objetos
- Git y GitHub

---

## 📁 Estructura del Proyecto

El código fuente se encuentra organizado de la siguiente manera:

```text
src/
├── main/
│   └── java/
│       ├── config/
│       │   └── ConexionDB.java
│       ├── dao/
│       │   ├── EntregaDAO.java
│       │   ├── PedidoDAO.java
│       │   └── RepartidorDAO.java
│       ├── model/
│       │   ├── Entrega.java
│       │   ├── EstadoPedido.java
│       │   ├── Pedido.java
│       │   ├── Repartidor.java
│       │   └── TipoPedido.java
│       └── ui/
│           ├── GestionEntregasPanel.java
│           ├── GestionPedidosPanel.java
│           ├── GestionRepartidoresPanel.java
│           └── VentanaPrincipal.java
