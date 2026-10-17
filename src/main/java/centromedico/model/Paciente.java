package centromedico.model;

import java.time.LocalDate;

public class Paciente extends Persona {

    private String dni;
    private LocalDate fechaNacimiento;
    private Tutor tutor;
    private ObraSocial obraSocial;

    public Paciente(
            int id,
            String nombre,
            String apellido,
            String dni,
            LocalDate fechaNacimiento,
            Tutor tutor,
            ObraSocial obraSocial
    ) {
        super(id, nombre, apellido);
        setDni(dni);
        setFechaNacimiento(fechaNacimiento);
        setTutor(tutor);
        setObraSocial(obraSocial);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException(
                    "El DNI del paciente es obligatorio."
            );
        }

        this.dni = dni.trim();
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException(
                    "La fecha de nacimiento es obligatoria."
            );
        }

        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de nacimiento no puede ser futura."
            );
        }

        this.fechaNacimiento = fechaNacimiento;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        if (tutor == null) {
            throw new IllegalArgumentException(
                    "El paciente debe tener un tutor asociado."
            );
        }

        this.tutor = tutor;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        // Es válida la ausencia de obra social.
        this.obraSocial = obraSocial;
    }

    @Override
    public String obtenerDescripcion() {
        return "Paciente: " + getNombre() + " " + getApellido()
                + " | DNI: " + dni;
    }
}