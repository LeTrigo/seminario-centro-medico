package centromedico.model;

public class ObraSocial {

    private int idObraSocial;
    private String nombre;

    public ObraSocial(int idObraSocial, String nombre) {
        if (idObraSocial < 0) {
            throw new IllegalArgumentException(
                    "El identificador no puede ser negativo."
            );
        }

        this.idObraSocial = idObraSocial;
        setNombre(nombre);
    }

    public int getIdObraSocial() {
        return idObraSocial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la obra social es obligatorio."
            );
        }

        this.nombre = nombre.trim();
    }

    @Override
    public String toString() {
        return nombre;
    }
}