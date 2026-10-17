package centromedico.app;

import centromedico.model.*;
import centromedico.dao.PacienteDAO;

import centromedico.service.TurnoNoDisponibleException;
import centromedico.service.TurnoService;
import java.time.LocalTime;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DemostracionPOO {

    public static void main(String[] args) {

        // 1. Crear los objetos necesarios para el ejemplo.
        Tutor tutor = new Tutor(
                1,
                "Ana",
                "Martínez",
                "30111222",
                "3804123456"
        );

        ObraSocial obraSocial = new ObraSocial(1, "OSDE");

        Paciente paciente = new Paciente(
                1,
                "Emma",
                "López",
                "49123456",
                LocalDate.of(2019, 5, 12),
                tutor,
                obraSocial
        );

        Especialidad especialidad = new Especialidad(1, "Pediatría");

        Usuario usuario = new Usuario(
                1,
                "dra.garcia",
                "PROFESIONAL"
        );

        Profesional profesional = new Profesional(
                1,
                "Laura",
                "García",
                "MP-1001",
                especialidad,
                usuario
        );

        // 2. Demostrar abstracción, herencia y polimorfismo.
        List<Persona> personas = new ArrayList<>();

        personas.add(tutor);
        personas.add(paciente);
        personas.add(profesional);

        System.out.println("PERSONAS REGISTRADAS");
        System.out.println("--------------------");

        for (Persona persona : personas) {
            System.out.println(persona.obtenerDescripcion());
        }

        System.out.println("--------------------");
        System.out.println("Cantidad de objetos: " + personas.size());

        // 3. Crear una historia clínica para el paciente.
        HistoriaClinica historia = new HistoriaClinica(
                1,
                LocalDate.of(2020, 1, 10),
                paciente
        );

        // 4. Crear dos consultas con fechas diferentes.
        Consulta consultaReciente = new Consulta(
                1,
                LocalDate.of(2026, 9, 10),
                7,
                new BigDecimal("24.50"),
                new BigDecimal("122.00"),
                "Control pediátrico",
                "Evolución favorable",
                "Continuar controles periódicos",
                "Sin alergias conocidas",
                "Calendario de vacunación completo",
                null,
                "Control de rutina",
                historia,
                profesional
        );

        Consulta consultaAnterior = new Consulta(
                2,
                LocalDate.of(2026, 8, 15),
                7,
                new BigDecimal("24.00"),
                new BigDecimal("121.00"),
                "Consulta por tos",
                "Cuadro respiratorio leve",
                "Hidratación y control evolutivo",
                "Sin alergias conocidas",
                "Calendario de vacunación completo",
                null,
                "Reevaluar si persisten los síntomas",
                historia,
                profesional
        );

        // 5. Agregar ambas consultas a la historia clínica.
        historia.agregarConsulta(consultaReciente);
        historia.agregarConsulta(consultaAnterior);

        System.out.println();
        System.out.println("HISTORIA CLÍNICA");
        System.out.println("----------------");
        System.out.println(historia);

        System.out.println();
        System.out.println("CONSULTAS ORDENADAS POR FECHA");
        System.out.println("-----------------------------");

        for (Consulta consulta : historia.obtenerConsultasOrdenadasPorFecha()) {
            System.out.println(consulta);
        }

        // 6. Demostrar el manejo de excepciones.
        System.out.println();
        System.out.println("PRUEBA DE VALIDACIÓN");
        System.out.println("--------------------");

        try {
            Consulta consultaInvalida = new Consulta(
                    3,
                    LocalDate.now(),
                    null,
                    null,
                    null,
                    " ",
                    "Diagnóstico de prueba",
                    "Tratamiento de prueba",
                    null,
                    null,
                    null,
                    null,
                    historia,
                    profesional
            );

            historia.agregarConsulta(consultaInvalida);

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Validación correcta: " + e.getMessage()
            );
        }

        // 7. Probar las reglas de reserva de turnos.
        System.out.println();
        System.out.println("PRUEBA DEL SERVICIO DE TURNOS");
        System.out.println("-----------------------------");

        TurnoService servicioTurnos = new TurnoService();

        LocalDate fechaTurnos = LocalDate.of(2026, 10, 20);

// Primera reserva: horario de 09:00 a 09:20.
        Turno turnoInicial = new Turno(
                1,
                fechaTurnos,
                LocalTime.of(9, 0),
                EstadoTurno.RESERVADO,
                TipoTurno.NORMAL,
                "Control pediátrico",
                paciente,
                profesional
        );

        try {
            servicioTurnos.reservarTurno(turnoInicial);
            System.out.println("Reserva aceptada: " + turnoInicial);

        } catch (TurnoNoDisponibleException e) {
            System.out.println("No se pudo reservar: " + e.getMessage());
        }

// Segunda reserva: comienza a las 09:20.
// No se superpone con el turno anterior.
        Turno turnoContinuo = new Turno(
                2,
                fechaTurnos,
                LocalTime.of(9, 20),
                EstadoTurno.RESERVADO,
                TipoTurno.NORMAL,
                "Consulta de seguimiento",
                paciente,
                profesional
        );

        try {
            servicioTurnos.reservarTurno(turnoContinuo);
            System.out.println("Reserva aceptada: " + turnoContinuo);

        } catch (TurnoNoDisponibleException e) {
            System.out.println("No se pudo reservar: " + e.getMessage());
        }

// Tercera reserva: comienza a las 09:10.
// Debe rechazarse porque se superpone con el turno de las 09:00.
        Turno turnoConflicto = new Turno(
                3,
                fechaTurnos,
                LocalTime.of(9, 10),
                EstadoTurno.RESERVADO,
                TipoTurno.NORMAL,
                "Turno con horario ocupado",
                paciente,
                profesional
        );

        try {
            servicioTurnos.reservarTurno(turnoConflicto);
            System.out.println("ERROR: se aceptó un turno superpuesto.");

        } catch (TurnoNoDisponibleException e) {
            System.out.println("Conflicto detectado correctamente: "
                    + e.getMessage());
        }

// Mostrar los turnos que fueron aceptados.
        System.out.println();
        System.out.println("TURNOS REGISTRADOS");
        System.out.println("------------------");

        for (Turno turno : servicioTurnos.obtenerTurnos()) {
            System.out.println(turno);
        }

        // 8. Comprobar el conflicto de horario del paciente
// aunque intervenga otro profesional.

        Usuario usuarioAlternativo = new Usuario(
                2,
                "dra.perez",
                "PROFESIONAL"
        );

        Profesional profesionalAlternativo = new Profesional(
                2,
                "Carla",
                "Pérez",
                "MP-2002",
                especialidad,
                usuarioAlternativo
        );

        Turno turnoPacienteOcupado = new Turno(
                4,
                fechaTurnos,
                LocalTime.of(9, 10),
                EstadoTurno.RESERVADO,
                TipoTurno.NORMAL,
                "Consulta con otro profesional",
                paciente,
                profesionalAlternativo
        );

        try {
            servicioTurnos.reservarTurno(turnoPacienteOcupado);
            System.out.println("ERROR: se aceptó un turno superpuesto para el paciente.");

        } catch (TurnoNoDisponibleException e) {
            System.out.println(
                    "Conflicto del paciente detectado correctamente: "
                            + e.getMessage()
            );
        }

        // 9. Probar la búsqueda de paciente por DNI en MySQL (modelo normalizado).
        System.out.println();
        System.out.println("BÚSQUEDA DE PACIENTE POR DNI (MYSQL)");
        System.out.println("------------------------------------");

        PacienteDAO pacienteDAO = new PacienteDAO();
        Paciente pacienteEncontrado = pacienteDAO.buscarPorDni("49123456");

        if (pacienteEncontrado != null) {
            System.out.println(pacienteEncontrado.obtenerDescripcion());
            System.out.println(pacienteEncontrado.getTutor().obtenerDescripcion());
            System.out.println("Obra social: " + (pacienteEncontrado.getObraSocial() != null
                    ? pacienteEncontrado.getObraSocial().getNombre()
                    : "Sin obra social"));
        } else {
            System.out.println("No se encontró un paciente con ese DNI.");
        }

    }
}