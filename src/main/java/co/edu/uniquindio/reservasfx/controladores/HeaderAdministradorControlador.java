package co.edu.uniquindio.reservasfx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HeaderAdministradorControlador {
    @FXML
    private Button casaBtn;
    @FXML
    private Button apartamentoBtn;
    @FXML
    private Button administracionBtn;
    @FXML
    private Button cerrarSesionBtn;
    @FXML
    private Button hotelBtn;
    @FXML
    private Button inicioBtn;
    @FXML
    private Button contraseniaBtn;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    @FXML
    void inicioAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
    }

    @FXML
    void casaBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferiorPersonalizadoFiltrado(
                "/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml",
                "Información de Casas");
    }

    @FXML
    void apartamentoBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferiorPersonalizadoFiltrado(
                "/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml",
                "Información de Apartamentos");
    }

    @FXML
    void hotelBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferiorPersonalizadoFiltrado(
                "/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml",
                "Información de Hoteles");
    }

    @FXML
    void administracionBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/panelAdministracion.fxml");
    }

    @FXML
    void contraseniaBtnAction(ActionEvent event) {
        controlador.navegarVentana(contraseniaBtn, "/co/edu/uniquindio/reservasfx/cambioContrasenia.fxml", "Cambiar Contraseña");
    }

    @FXML
    void cerrarSesionAction(ActionEvent event) {
        panePrincipalControlador.actualizar("/co/edu/uniquindio/reservasfx/headerPrincipal.fxml",
                "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
        controlador.getSesion().setUsuario(null);
    }
}
