DROP DATABASE IF EXISTS centromedico;

CREATE DATABASE centromedico
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE centromedico;

CREATE TABLE usuario (
                         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(50) NOT NULL UNIQUE,
                         password_hash VARCHAR(255) NOT NULL,
                         rol ENUM('SECRETARIA', 'PROFESIONAL') NOT NULL,
                         activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE tutor (
                       id_tutor INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(50) NOT NULL,
                       apellido VARCHAR(50) NOT NULL,
                       dni VARCHAR(15) NOT NULL UNIQUE,
                       telefono VARCHAR(20)
);

CREATE TABLE especialidad (
                              id_especialidad INT AUTO_INCREMENT PRIMARY KEY,
                              nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE profesional (
                             id_profesional INT AUTO_INCREMENT PRIMARY KEY,
                             nombre VARCHAR(50) NOT NULL,
                             apellido VARCHAR(50) NOT NULL,
                             matricula VARCHAR(30) NOT NULL UNIQUE,
                             id_especialidad INT NOT NULL,

                             CONSTRAINT fk_profesional_especialidad
                                 FOREIGN KEY (id_especialidad)
                                     REFERENCES especialidad(id_especialidad)
);

CREATE TABLE paciente (
                          id_paciente INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(50) NOT NULL,
                          apellido VARCHAR(50) NOT NULL,
                          dni VARCHAR(15) NOT NULL UNIQUE,
                          fecha_nacimiento DATE NOT NULL,
                          obra_social VARCHAR(100),
                          id_tutor INT NOT NULL,

                          CONSTRAINT fk_paciente_tutor
                              FOREIGN KEY (id_tutor)
                                  REFERENCES tutor(id_tutor)
);

CREATE TABLE historia_clinica (
                                  id_historia INT AUTO_INCREMENT PRIMARY KEY,
                                  fecha_apertura DATE NOT NULL,
                                  id_paciente INT NOT NULL UNIQUE,

                                  CONSTRAINT fk_historia_paciente
                                      FOREIGN KEY (id_paciente)
                                          REFERENCES paciente(id_paciente)
);

CREATE TABLE disponibilidad (
                                id_disponibilidad INT AUTO_INCREMENT PRIMARY KEY,
                                fecha DATE NOT NULL,
                                hora_inicio TIME NOT NULL,
                                hora_fin TIME NOT NULL,
                                id_profesional INT NOT NULL,

                                CONSTRAINT fk_disponibilidad_profesional
                                    FOREIGN KEY (id_profesional)
                                        REFERENCES profesional(id_profesional),

                                CONSTRAINT chk_horario_disponibilidad
                                    CHECK (hora_fin > hora_inicio)
);

CREATE TABLE turno (
                       id_turno INT AUTO_INCREMENT PRIMARY KEY,
                       fecha DATE NOT NULL,
                       hora TIME NOT NULL,
                       estado ENUM(
        'RESERVADO',
        'CONFIRMADO',
        'ATENDIDO',
        'CANCELADO',
        'AUSENTE'
    ) NOT NULL DEFAULT 'RESERVADO',
                       tipo ENUM('NORMAL', 'SOBRETURNO') NOT NULL DEFAULT 'NORMAL',
                       observaciones VARCHAR(255),
                       id_paciente INT NOT NULL,
                       id_profesional INT NOT NULL,

                       CONSTRAINT fk_turno_paciente
                           FOREIGN KEY (id_paciente)
                               REFERENCES paciente(id_paciente),

                       CONSTRAINT fk_turno_profesional
                           FOREIGN KEY (id_profesional)
                               REFERENCES profesional(id_profesional)
);

CREATE TABLE consulta (
                          id_consulta INT AUTO_INCREMENT PRIMARY KEY,
                          fecha DATE NOT NULL,
                          edad INT,
                          peso DECIMAL(5,2),
                          talla DECIMAL(5,2),
                          motivo TEXT NOT NULL,
                          diagnostico TEXT NOT NULL,
                          tratamiento TEXT NOT NULL,
                          alergias TEXT,
                          vacunacion TEXT,
                          antecedentes TEXT,
                          observaciones TEXT,
                          id_historia INT NOT NULL,
                          id_profesional INT NOT NULL,

                          CONSTRAINT fk_consulta_historia
                              FOREIGN KEY (id_historia)
                                  REFERENCES historia_clinica(id_historia),

                          CONSTRAINT fk_consulta_profesional
                              FOREIGN KEY (id_profesional)
                                  REFERENCES profesional(id_profesional)
);

CREATE TABLE modificacion_consulta (
                                       id_modificacion INT AUTO_INCREMENT PRIMARY KEY,
                                       fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       id_consulta INT NOT NULL,
                                       id_profesional INT NOT NULL,

                                       CONSTRAINT fk_modificacion_consulta
                                           FOREIGN KEY (id_consulta)
                                               REFERENCES consulta(id_consulta),

                                       CONSTRAINT fk_modificacion_profesional
                                           FOREIGN KEY (id_profesional)
                                               REFERENCES profesional(id_profesional)
);

SHOW TABLES;