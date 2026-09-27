USE centromedico;

-- USUARIOS
INSERT INTO usuario (username, password_hash, rol)
VALUES
    ('secretaria', SHA2('demo123', 256), 'SECRETARIA'),
    ('dra.garcia', SHA2('demo123', 256), 'PROFESIONAL'),
    ('dra.ruiz', SHA2('demo123', 256), 'PROFESIONAL'),
    ('dra.lopez', SHA2('demo123', 256), 'PROFESIONAL');

-- ESPECIALIDADES
INSERT INTO especialidad (nombre)
VALUES
    ('Pediatría'),
    ('Cardiología pediátrica');

-- PROFESIONALES
INSERT INTO profesional
(nombre, apellido, matricula, id_especialidad)
VALUES
    ('Laura', 'García', 'MP-1001', 1),
    ('Mariana', 'Ruiz', 'MP-1002', 1),
    ('Carolina', 'López', 'MP-2001', 2);

-- TUTORES
INSERT INTO tutor
(nombre, apellido, dni, telefono)
VALUES
    ('Ana', 'Martínez', '30111222', '3804000001'),
    ('Pablo', 'Pérez', '28999888', '3804000002');

-- PACIENTES
INSERT INTO paciente
(nombre, apellido, dni, fecha_nacimiento, obra_social, id_tutor)
VALUES
    ('Emma', 'López', '49123456', '2019-03-15', 'OSDE', 1),
    ('Mateo', 'Pérez', '50111222', '2020-06-22', 'Swiss Medical', 2),
    ('Valentina', 'Torres', '48777888', '2018-09-10', NULL, 1);

-- HISTORIAS CLÍNICAS
INSERT INTO historia_clinica
(fecha_apertura, id_paciente)
VALUES
    ('2026-01-10', 1),
    ('2026-02-15', 2),
    ('2026-03-01', 3);

-- DISPONIBILIDADES
INSERT INTO disponibilidad
(fecha, hora_inicio, hora_fin, id_profesional)
VALUES
    ('2026-10-01', '09:00:00', '13:00:00', 1),
    ('2026-10-02', '14:00:00', '18:00:00', 1),
    ('2026-10-01', '08:00:00', '12:00:00', 2),
    ('2026-10-03', '09:00:00', '13:00:00', 3);

-- TURNOS
INSERT INTO turno
(fecha, hora, estado, tipo, observaciones, id_paciente, id_profesional)
VALUES
    ('2026-10-01', '09:00:00', 'CONFIRMADO', 'NORMAL', NULL, 1, 1),
    ('2026-10-01', '10:00:00', 'RESERVADO', 'NORMAL', NULL, 2, 1),
    ('2026-10-01', '11:00:00', 'CONFIRMADO', 'SOBRETURNO',
     'Sobreturno autorizado', 3, 1);

-- CONSULTAS
INSERT INTO consulta
(
    fecha, edad, peso, talla, motivo,
    diagnostico, tratamiento,
    alergias, vacunacion, antecedentes,
    observaciones, id_historia, id_profesional
)
VALUES
    (
        '2026-08-15', 7, 24.50, 122.00,
        'Dolor de garganta y fiebre',
        'Faringitis',
        'Tratamiento sintomático y control',
        'Sin alergias conocidas',
        'Calendario completo',
        NULL,
        'Control en caso de persistencia de síntomas',
        1, 1
    ),
    (
        '2026-09-10', 6, 23.80, 121.50,
        'Control pediátrico',
        'Evolución favorable',
        'Continuar controles periódicos',
        'Sin alergias conocidas',
        'Calendario completo',
        NULL,
        NULL,
        1, 2
    );

-- REGISTRO DE MODIFICACIÓN
INSERT INTO modificacion_consulta
(id_consulta, id_profesional)
VALUES
    (2, 2);