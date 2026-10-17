USE centromedico;

-- ==================================================
-- 1. CATÁLOGO DE OBRAS SOCIALES
-- ==================================================

CREATE TABLE IF NOT EXISTS obra_social (
                                           id_obra_social INT AUTO_INCREMENT PRIMARY KEY,
                                           nombre VARCHAR(100) NOT NULL UNIQUE
    ) ENGINE=InnoDB;


-- Agregamos la referencia normalizada al paciente.
-- Ejecutar esta migración una sola vez.

ALTER TABLE paciente
    ADD COLUMN id_obra_social INT NULL;


-- Copiamos las obras sociales existentes al catálogo.
-- No agregamos valores nulos ni nombres vacíos.

INSERT INTO obra_social (nombre)
SELECT DISTINCT TRIM(p.obra_social)
FROM paciente p
WHERE p.obra_social IS NOT NULL
  AND TRIM(p.obra_social) <> ''
  AND NOT EXISTS (
    SELECT 1
    FROM obra_social os
    WHERE os.nombre = TRIM(p.obra_social)
);


-- Relacionamos cada paciente con su obra social.

UPDATE paciente p
    JOIN obra_social os
ON os.nombre = TRIM(p.obra_social)
    SET p.id_obra_social = os.id_obra_social
WHERE p.obra_social IS NOT NULL
  AND TRIM(p.obra_social) <> '';


-- Creamos la clave foránea.

ALTER TABLE paciente
    ADD CONSTRAINT fk_paciente_obra_social
        FOREIGN KEY (id_obra_social)
            REFERENCES obra_social(id_obra_social);


-- ==================================================
-- 2. RELACIÓN ENTRE PROFESIONAL Y USUARIO
-- ==================================================

-- Cada profesional podrá vincularse con su cuenta.
-- Primero dejamos la columna nullable para verificar
-- que todas las relaciones se asignen correctamente.

ALTER TABLE profesional
    ADD COLUMN id_usuario INT NULL;


-- Vinculamos las cuentas de demostración
-- según la matrícula de cada profesional.

UPDATE profesional p
    JOIN usuario u
ON u.username = CASE p.matricula
    WHEN 'MP-1001' THEN 'dra.garcia'
    WHEN 'MP-1002' THEN 'dra.ruiz'
    WHEN 'MP-2001' THEN 'dra.lopez'
    ELSE NULL
END
SET p.id_usuario = u.id_usuario;


-- Evitamos asociar una cuenta a más de un profesional.

ALTER TABLE profesional
    ADD CONSTRAINT uq_profesional_usuario
        UNIQUE (id_usuario);


-- Garantizamos que las cuentas vinculadas existan.

ALTER TABLE profesional
    ADD CONSTRAINT fk_profesional_usuario
        FOREIGN KEY (id_usuario)
            REFERENCES usuario(id_usuario);