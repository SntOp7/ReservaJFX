package co.edu.uniquindio.reservasfx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HeaderSesionControlador {
    @FXML
    private Button casaBtn;
    @FXML
    private Button apartamentoBtn;
    @FXML
    private Button cerrarSesionBtn;
    @FXML
    private Button hotelBtn;
    @FXML
    private Button ofertaBtn;
    @FXML
    private Button notificacionBtn;
    @FXML
    private Button inicioBtn;
    @FXML
    private Button cuentaBtn;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    @FXML
    void inicioAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
    }

    @FXML
    void casaBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml");
    }

    @FXML
    void apartamentoBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml");
    }

    @FXML
    void hotelBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml");
    }

    @FXML
    void ofertaBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferiorPersonalizado("/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
    }

    @FXML
    void cuentaBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/cuentaCliente.fxml");
    }

    @FXML
    void cerrarSesionAction(ActionEvent event) {
        panePrincipalControlador.actualizar("/co/edu/uniquindio/reservasfx/headerPrincipal.fxml",
                "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
        controlador.getSesion().setUsuario(null);
    }

    @FXML
    void notificacionAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/notificacionesNoLeidas.fxml");
    }
}
