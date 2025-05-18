package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

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
    @FXML
    public static ImageView imagenNotificacion;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    @FXML
    void initialize() {
        cargarImagenNotificacion(controlador, imagenNotificacion);
    }

    public static void cargarImagenNotificacion(ControladorPrincipal controlador, ImageView imagenNotificacion) {
        try {
            ArrayList<Notificacion> notificacionesNoLeidas = controlador.getEmpresa().getModuloUsuarioServicios()
                    .obtenerNotificacionesNoLeidas(controlador.getSesion().getUsuario().getCedula());
            if (!notificacionesNoLeidas.isEmpty()) {
                Image imagen = ControladorPrincipal.cargarImagenSeleccionada("notificacionNovedad.png");
                imagenNotificacion.setImage(imagen);
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void inicioAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
    }

    @FXML
    void casaBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferiorPersonalizadoFiltrado(
                "/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml",
                "Información de Casas", TipoAlojamiento.CASA);
    }

    @FXML
    void apartamentoBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferiorPersonalizadoFiltrado(
                "/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml",
                "Información de Apartamentos", TipoAlojamiento.APARTAMENTO);
    }

    @FXML
    void hotelBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferiorPersonalizadoFiltrado(
                "/co/edu/uniquindio/reservasfx/filtradoAlojamiento.fxml",
                "Información de Hoteles", TipoAlojamiento.HOTEL);
    }

    @FXML
    void ofertaBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferiorPersonalizadoOferta(
                "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
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
