package co.edu.uniquindio.reservasfx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HeaderPrincipalControlador {
    @FXML
    private Button registrarseBtn;
    @FXML
    private Button inicioBtn;
    @FXML
    private Button iniciarSesionBtn;

    PanePrincipalControlador inicioControlador = PanePrincipalControlador.getInstancia();

    @FXML
    void inicioAction(ActionEvent event) {
        inicioControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
    }

    @FXML
    void iniciarSesionAction(ActionEvent event) {
        inicioControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/inicioSesion.fxml");
    }

    @FXML
    void registrarseAction(ActionEvent event) {
        inicioControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/registroCliente.fxml");
    }
}
