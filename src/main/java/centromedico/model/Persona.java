package centromedico.model;

public abstract class Persona {

    private int id;
    private String nombre;
    private String apellido;

    protected Persona(int id, String nombre, String apellido) {

        if (id < 0) {
            throw new IllegalArgumentException(
                    "El identificador no puede ser negativo."
            );
        }

        this.id = id;
        setNombre(nombre);
        setApellido(apellido);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio."
            );
        }

        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {

        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException(
                    "El apellido es obligatorio."
            );
        }

        this.apellido = apellido.trim();
    }

    public abstract String obtenerDescripcion();

    @Override
    public String toString() {
        return obtenerDescripcion();
    }
}