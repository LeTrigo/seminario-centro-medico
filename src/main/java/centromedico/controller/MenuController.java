package centromedico.controller;

import centromedico.model.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Label lblUsuario;

    @FXML
    private Button btnPacientes;

    @FXML
    private Button btnTurnos;

    @FXML
    private Button btnAgenda;

    @FXML
    private Button btnHistoriaClinica;

    @FXML
    private Button btnRegistrarConsulta;

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {

        this.usuario = usuario;

        lblUsuario.setText(
                "Usuario: " + usuario.getUsername()
                        + " | Rol: " + usuario.getRol()
        );

        configurarPermisos();
    }

    private void configurarPermisos() {

        if (usuario.getRol().equals("SECRETARIA")) {

            btnPacientes.setVisible(true);
            btnPacientes.setManaged(true);

            btnTurnos.setVisible(true);
            btnTurnos.setManaged(true);

            btnAgenda.setVisible(true);
            btnAgenda.setManaged(true);

            // La secretaria NO accede a información clínica
            btnHistoriaClinica.setVisible(false);
            btnHistoriaClinica.setManaged(false);

            btnRegistrarConsulta.setVisible(false);
            btnRegistrarConsulta.setManaged(false);

        } else if (usuario.getRol().equals("PROFESIONAL")) {

            // Funciones administrativas no necesarias
            btnPacientes.setVisible(false);
            btnPacientes.setManaged(false);

            btnTurnos.setVisible(false);
            btnTurnos.setManaged(false);

            btnAgenda.setVisible(true);
            btnAgenda.setManaged(true);

            // Funciones clínicas
            btnHistoriaClinica.setVisible(true);
            btnHistoriaClinica.setManaged(true);

            btnRegistrarConsulta.setVisible(true);
            btnRegistrarConsulta.setManaged(true);
        }
    }

    @FXML
    private void mostrarPrototipo(ActionEvent event) {

        Button boton = (Button) event.getSource();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Prototipo");
        alert.setHeaderText(boton.getText());

        alert.setContentText(
                "Funcionalidad contemplada en el prototipo del sistema."
        );

        alert.showAndWait();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/view/login.fxml")
                    );

            Scene scene = new Scene(loader.load());

            Stage stage =
                    (Stage) ((Button) event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(scene);
            stage.setTitle("Centro Médico Pediátrico");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}