package centromedico.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/centromedico";

    private static final String USUARIO = "root";

    // Si tu MySQL tiene contraseña, escribila aquí.
    private static final String PASSWORD = "ornitorrinco56?";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}