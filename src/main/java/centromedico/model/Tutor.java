package centromedico.model;

public class Tutor extends Persona {

    private String dni;
    private String telefono;

    public Tutor(
            int id,
            String nombre,
            String apellido,
            String dni,
            String telefono
    ) {
        super(id, nombre, apellido);
        setDni(dni);
        setTelefono(telefono);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException(
                    "El DNI del tutor es obligatorio."
            );
        }

        this.dni = dni.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = (telefono == null || telefono.isBlank())
                ? null
                : telefono.trim();
    }

    @Override
    public String obtenerDescripcion() {
        return "Tutor: " + getNombre() + " " + getApellido()
                + " | DNI: " + dni;
    }
}