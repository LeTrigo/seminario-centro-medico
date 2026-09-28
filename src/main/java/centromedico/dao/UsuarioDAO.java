package centromedico.dao;

import centromedico.model.Usuario;
import centromedico.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario autenticar(String username, String password) {

        String sql = """
                SELECT id_usuario, username, rol
                FROM usuario
                WHERE username = ?
                  AND password_hash = SHA2(?, 256)
                  AND activo = TRUE
                """;

        try (
                Connection conexion = ConexionBD.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("username"),
                        rs.getString("rol")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}