package co.edu.uniquindio.reservasfx.controladores;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RecargarBilleteraControlador {

    @FXML
    private Button recargarBtn;

    @FXML
    private TextField recargaField;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    Sesion sesion = controlador.getSesion();

    @FXML
    void recargarBtnAction(ActionEvent event) {
        try {
            String contenido = recargaField.getText().trim();
            double monto = Double.parseDouble(contenido);
            String cedula = sesion.getUsuario().getCedula();
            controlador.getEmpresa().getModuloUsuarioServicios().recargarBilleteraCliente(cedula, monto);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
