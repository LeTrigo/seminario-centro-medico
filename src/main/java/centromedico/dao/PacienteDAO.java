package centromedico.dao;

import centromedico.model.ObraSocial;
import centromedico.model.Paciente;
import centromedico.model.Tutor;
import centromedico.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public Paciente buscarPorDni(String dni) {
        String sql = """
                SELECT p.id_paciente, p.nombre, p.apellido, p.dni, p.fecha_nacimiento,
                       t.id_tutor, t.nombre AS nombre_tutor, t.apellido AS apellido_tutor,
                       t.dni AS dni_tutor, t.telefono AS telefono_tutor,
                       os.id_obra_social, os.nombre AS nombre_obra_social
                FROM paciente p
                JOIN tutor t ON t.id_tutor = p.id_tutor
                LEFT JOIN obra_social os ON os.id_obra_social = p.id_obra_social
                WHERE p.dni = ?
                """;

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPaciente(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar paciente por DNI.", e);
        }
        return null;
    }

    public List<Paciente> buscarPorNombre(String criterio) {
        String sql = """
                SELECT p.id_paciente, p.nombre, p.apellido, p.dni, p.fecha_nacimiento,
                       t.id_tutor, t.nombre AS nombre_tutor, t.apellido AS apellido_tutor,
                       t.dni AS dni_tutor, t.telefono AS telefono_tutor,
                       os.id_obra_social, os.nombre AS nombre_obra_social
                FROM paciente p
                JOIN tutor t ON t.id_tutor = p.id_tutor
                LEFT JOIN obra_social os ON os.id_obra_social = p.id_obra_social
                WHERE p.nombre LIKE ? OR p.apellido LIKE ?
                ORDER BY p.apellido, p.nombre
                """;

        List<Paciente> pacientes = new ArrayList<>();
        String filtro = "%" + criterio.trim() + "%";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, filtro);
            ps.setString(2, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pacientes.add(mapearPaciente(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pacientes por nombre.", e);
        }
        return pacientes;
    }

    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Tutor tutor = new Tutor(
                rs.getInt("id_tutor"),
                rs.getString("nombre_tutor"),
                rs.getString("apellido_tutor"),
                rs.getString("dni_tutor"),
                rs.getString("telefono_tutor")
        );

        ObraSocial obraSocial = null;
        int idObraSocial = rs.getInt("id_obra_social");
        if (!rs.wasNull()) {
            obraSocial = new ObraSocial(
                    idObraSocial,
                    rs.getString("nombre_obra_social")
            );
        }

        return new Paciente(
                rs.getInt("id_paciente"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                tutor,
                obraSocial
        );
    }
}