package centromedico.model;

public class Especialidad {

    private int idEspecialidad;
    private String nombre;

    public Especialidad(int idEspecialidad, String nombre) {
        if (idEspecialidad < 0) {
            throw new IllegalArgumentException(
                    "El identificador no puede ser negativo."
            );
        }

        this.idEspecialidad = idEspecialidad;
        setNombre(nombre);
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la especialidad es obligatorio."
            );
        }

        this.nombre = nombre.trim();
    }

    @Override
    public String toString() {
        return nombre;
    }
}