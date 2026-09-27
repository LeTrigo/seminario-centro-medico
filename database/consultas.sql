-- Consulta 1 - Agenda de una profesional
SELECT
    t.fecha,
    t.hora,
    CONCAT(pa.apellido, ', ', pa.nombre) AS paciente,
    CONCAT(pr.apellido, ', ', pr.nombre) AS profesional,
    t.tipo,
    t.estado
FROM turno t
         JOIN paciente pa
              ON pa.id_paciente = t.id_paciente
         JOIN profesional pr
              ON pr.id_profesional = t.id_profesional
WHERE t.id_profesional = 1
ORDER BY t.fecha, t.hora;

-- Consulta 2 - Historia clínica de un paciente
SELECT
    c.fecha,
    c.edad,
    c.peso,
    c.talla,
    c.motivo,
    c.diagnostico,
    c.tratamiento,
    CONCAT(pr.apellido, ', ', pr.nombre) AS profesional
FROM consulta c
         JOIN historia_clinica hc
              ON hc.id_historia = c.id_historia
         JOIN paciente pa
              ON pa.id_paciente = hc.id_paciente
         JOIN profesional pr
              ON pr.id_profesional = c.id_profesional
WHERE pa.id_paciente = 1
ORDER BY c.fecha DESC;

-- Consulta 3 - Disponibilidad profesional
SELECT
    d.fecha,
    d.hora_inicio,
    d.hora_fin,
    CONCAT(p.apellido, ', ', p.nombre) AS profesional
FROM disponibilidad d
         JOIN profesional p
              ON p.id_profesional = d.id_profesional
WHERE d.id_profesional = 1
ORDER BY d.fecha, d.hora_inicio;