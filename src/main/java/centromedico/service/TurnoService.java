package centromedico.service;

import centromedico.model.EstadoTurno;
import centromedico.model.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TurnoService {

    private static final long DURACION_TURNO_MINUTOS = 20;

    private final List<Turno> turnos = new ArrayList<>();

    public void reservarTurno(Turno nuevoTurno)
            throws TurnoNoDisponibleException {

        Objects.requireNonNull(
                nuevoTurno,
                "El turno no puede ser nulo."
        );

        for (Turno existente : turnos) {

            // Los turnos cancelados no bloquean el horario.
            if (existente.getEstado() == EstadoTurno.CANCELADO) {
                continue;
            }

            // Solo comparamos turnos del mismo día.
            if (!existente.getFecha().equals(nuevoTurno.getFecha())) {
                continue;
            }

            // Verificamos si sus horarios se superponen.
            if (!seSuperponen(
                    existente.getHora(),
                    nuevoTurno.getHora())) {
                continue;
            }

            // Un profesional no puede atender dos turnos
            // que se superpongan.
            if (existente.getProfesional().getId()
                    == nuevoTurno.getProfesional().getId()) {

                throw new TurnoNoDisponibleException(
                        "El profesional ya tiene un turno en ese horario."
                );
            }

            // Un paciente tampoco puede tener dos turnos
            // que se superpongan.
            if (existente.getPaciente().getId()
                    == nuevoTurno.getPaciente().getId()) {

                throw new TurnoNoDisponibleException(
                        "El paciente ya tiene un turno en ese horario."
                );
            }
        }

        // Si no hay conflictos, registramos el turno en la lista.
        turnos.add(nuevoTurno);
    }

    private boolean seSuperponen(
            LocalTime horaExistente,
            LocalTime horaNueva) {

        long inicioExistente = horaExistente.toSecondOfDay();
        long finExistente = inicioExistente
                + DURACION_TURNO_MINUTOS * 60;

        long inicioNuevo = horaNueva.toSecondOfDay();
        long finNuevo = inicioNuevo
                + DURACION_TURNO_MINUTOS * 60;

        return inicioExistente < finNuevo
                && inicioNuevo < finExistente;
    }

    public List<Turno> obtenerTurnos() {
        return List.copyOf(turnos);
    }
}