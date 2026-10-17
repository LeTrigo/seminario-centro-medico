package centromedico.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {

    private int idTurno;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;
    private TipoTurno tipo;
    private String observaciones;
    private Paciente paciente;
    private Profesional profesional;

    public Turno(
            int idTurno,
            LocalDate fecha,
            LocalTime hora,
            EstadoTurno estado,
            TipoTurno tipo,
            String observaciones,
            Paciente paciente,
            Profesional profesional
    ) {
        if (idTurno < 0) {
            throw new IllegalArgumentException(
                    "El identificador del turno no puede ser negativo."
            );
        }

        this.idTurno = idTurno;
        setFecha(fecha);
        setHora(hora);
        setEstado(estado);
        setTipo(tipo);
        setObservaciones(observaciones);
        setPaciente(paciente);
        setProfesional(profesional);
    }

    public int getIdTurno() {
        return idTurno;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha del turno es obligatoria."
            );
        }

        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        if (hora == null) {
            throw new IllegalArgumentException(
                    "La hora del turno es obligatoria."
            );
        }

        this.hora = hora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado del turno es obligatorio."
            );
        }

        this.estado = estado;
    }

    public TipoTurno getTipo() {
        return tipo;
    }

    public void setTipo(TipoTurno tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "El tipo de turno es obligatorio."
            );
        }

        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        if (observaciones == null || observaciones.isBlank()) {
            this.observaciones = null;
        } else {
            this.observaciones = observaciones.trim();
        }
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException(
                    "El turno debe tener un paciente asociado."
            );
        }

        this.paciente = paciente;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        if (profesional == null) {
            throw new IllegalArgumentException(
                    "El turno debe tener una profesional asignada."
            );
        }

        this.profesional = profesional;
    }

    @Override
    public String toString() {
        return "Turno " + idTurno
                + " | Fecha: " + fecha
                + " | Hora: " + hora
                + " | Paciente: " + paciente.getNombre()
                + " " + paciente.getApellido()
                + " | Profesional: " + profesional.getNombre()
                + " " + profesional.getApellido()
                + " | Estado: " + estado
                + " | Tipo: " + tipo;
    }
}