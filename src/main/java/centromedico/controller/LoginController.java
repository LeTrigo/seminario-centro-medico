package centromedico.controller;

import centromedico.dao.UsuarioDAO;
import centromedico.model.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    private final UsuarioDAO usuarioDAO =
            new UsuarioDAO();

    @FXML
    private void iniciarSesion() {

        String username = txtUsuario.getText();
        String password = txtPassword.getText();

        if (username.isBlank() || password.isBlank()) {

            lblMensaje.setText(
                    "Complete usuario y contraseña."
            );

            return;
        }

        Usuario usuario =
                usuarioDAO.autenticar(
                        username,
                        password
                );

        if (usuario == null) {

            lblMensaje.setText(
                    "Credenciales incorrectas."
            );

            return;
        }

        abrirMenu(usuario);
    }

    private void abrirMenu(Usuario usuario) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/view/menu.fxml")
                    );

            Scene scene =
                    new Scene(loader.load());

            MenuController menuController =
                    loader.getController();

            menuController.setUsuario(usuario);

            Stage stage =
                    (Stage) txtUsuario
                            .getScene()
                            .getWindow();

            stage.setScene(scene);
            stage.setTitle(
                    "Centro Médico Pediátrico - Menú"
            );

        } catch (Exception e) {

            e.printStackTrace();

            lblMensaje.setText(
                    "No se pudo abrir el menú principal."
            );
        }
    }
}