package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class EliminarCuentaControlador {


    @FXML
    private Button aceptarBtn;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    CuentaClienteControlador cuentaClienteControlador;
    Sesion sesion = controlador.getSesion();

    @FXML
    void aceptarBtnAction(ActionEvent event) {
        try {
            String cedula = sesion.getUsuario().getCedula();
            controlador.getEmpresa().getModuloUsuarioServicios().eliminarCliente(cedula);
            cuentaClienteControlador.limpiarCampos();

        }
        catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
