USE centromedico;

-- 1. Consulta de agenda por profesional y fecha
SELECT t.fecha, t.hora, CONCAT(pa.apellido, ', ', pa.nombre) AS paciente,
       CONCAT(pr.apellido, ', ', pr.nombre) AS profesional, t.tipo, t.estado
FROM turno t
         JOIN paciente pa ON pa.id_paciente = t.id_paciente
         JOIN profesional pr ON pr.id_profesional = t.id_profesional
WHERE t.id_profesional = 1
ORDER BY t.fecha, t.hora;

-- 2. Búsqueda de paciente por DNI (Incluye tutor y obra social)
SELECT p.id_paciente, p.nombre, p.apellido, p.dni, p.fecha_nacimiento, p.vacunacion_al_dia,
       os.nombre AS obra_social, t.nombre AS nombre_tutor, t.apellido AS apellido_tutor, t.telefono
FROM paciente p
         JOIN tutor t ON t.id_tutor = p.id_tutor
         LEFT JOIN obra_social os ON os.id_obra_social = p.id_obra_social
WHERE p.dni = '49123456';

-- 3. Consulta de historia clínica (Consultas cronológicas)
SELECT c.fecha, c.edad, c.peso, c.talla, c.motivo, c.diagnostico, c.tratamiento,
       CONCAT(pr.apellido, ', ', pr.nombre) AS profesional
FROM consulta c
         JOIN historia_clinica hc ON hc.id_historia = c.id_historia
         JOIN paciente pa ON pa.id_paciente = hc.id_paciente
         JOIN profesional pr ON pr.id_profesional = c.id_profesional
WHERE pa.id_paciente = 1
ORDER BY c.fecha DESC;

-- 4. Trazabilidad de modificaciones clínicas
SELECT mc.fecha_hora, CONCAT(p.apellido, ', ', p.nombre) AS profesional
FROM modificacion_consulta mc
         JOIN profesional p ON p.id_profesional = mc.id_profesional
WHERE mc.id_consulta = 2
ORDER BY mc.fecha_hora DESC;

-- 5. Consulta de disponibilidades
SELECT d.fecha, d.hora_inicio, d.hora_fin, CONCAT(p.apellido, ', ', p.nombre) AS profesional
FROM disponibilidad d
         JOIN profesional p ON p.id_profesional = d.id_profesional
WHERE d.id_profesional = 1
ORDER BY d.fecha, d.hora_inicio;