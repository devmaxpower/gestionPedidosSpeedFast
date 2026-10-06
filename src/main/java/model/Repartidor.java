package model;

/**
 * Clase que representa la entidad Repartidor dentro del sistema SpeedFast.
 * Mapea la tabla 'repartidores' de la base de datos[cite: 1].
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class Repartidor {

    private int id;
    private String nombre;

    /**
     * Constructor vacío por defecto.
     */
    public Repartidor() {
    }

    /**
     * Constructor para registrar un nuevo repartidor (sin ID asignado en BD)[cite: 1].
     *
     * @param nombre Nombre del repartidor.
     */
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor completo con ID y nombre[cite: 1].
     *
     * @param id Identificador único del repartidor en BD.
     * @param nombre Nombre del repartidor.
     */
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Sobrescribe el método toString() para mostrar un texto amigable en componentes
     * como JComboBox en la interfaz gráfica (p. ej. "1 - Juan Pérez")[cite: 1, 2].
     *
     * @return Cadena formateada "ID - Nombre".
     */
    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}