package centromedico.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HistoriaClinica {

    private int idHistoria;
    private LocalDate fechaApertura;
    private Paciente paciente;
    private final List<Consulta> consultas = new ArrayList<>();

    public HistoriaClinica(
            int idHistoria,
            LocalDate fechaApertura,
            Paciente paciente
    ) {
        if (idHistoria < 0) {
            throw new IllegalArgumentException(
                    "El identificador no puede ser negativo."
            );
        }

        this.idHistoria = idHistoria;
        setFechaApertura(fechaApertura);
        setPaciente(paciente);
    }

    public int getIdHistoria() {
        return idHistoria;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        if (fechaApertura == null) {
            throw new IllegalArgumentException(
                    "La fecha de apertura es obligatoria."
            );
        }

        this.fechaApertura = fechaApertura;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException(
                    "La historia clínica debe tener un paciente asociado."
            );
        }

        this.paciente = paciente;
    }

    public List<Consulta> getConsultas() {
        return List.copyOf(consultas);
    }

    public void agregarConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException(
                    "La consulta no puede ser nula."
            );
        }

        if (consulta.getHistoriaClinica() != this) {
            throw new IllegalArgumentException(
                    "La consulta pertenece a otra historia clínica."
            );
        }

        if (consultas.contains(consulta)) {
            throw new IllegalArgumentException(
                    "La consulta ya fue agregada."
            );
        }

        consultas.add(consulta);
    }

    public List<Consulta> obtenerConsultasOrdenadasPorFecha() {
        return consultas.stream()
                .sorted(Comparator.comparing(Consulta::getFecha))
                .toList();
    }

    @Override
    public String toString() {
        return "Historia clínica " + idHistoria
                + " | Paciente: "
                + paciente.getNombre() + " " + paciente.getApellido()
                + " | Cantidad de consultas: " + consultas.size();
    }
}