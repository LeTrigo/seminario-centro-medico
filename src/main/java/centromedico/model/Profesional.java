package centromedico.model;

public class Profesional extends Persona {

    private String matricula;
    private Especialidad especialidad;
    private Usuario usuario;

    public Profesional(
            int id,
            String nombre,
            String apellido,
            String matricula,
            Especialidad especialidad,
            Usuario usuario
    ) {
        super(id, nombre, apellido);
        setMatricula(matricula);
        setEspecialidad(especialidad);
        setUsuario(usuario);
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException(
                    "La matrícula profesional es obligatoria."
            );
        }

        this.matricula = matricula.trim();
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException(
                    "La especialidad es obligatoria."
            );
        }

        this.especialidad = especialidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "La profesional debe tener un usuario asociado."
            );
        }

        if (!"PROFESIONAL".equals(usuario.getRol())) {
            throw new IllegalArgumentException(
                    "El usuario asociado debe tener rol PROFESIONAL."
            );
        }

        this.usuario = usuario;
    }

    @Override
    public String obtenerDescripcion() {
        return "Profesional: " + getNombre() + " " + getApellido()
                + " | Matrícula: " + matricula
                + " | Especialidad: " + especialidad.getNombre();
    }
}