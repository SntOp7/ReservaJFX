package co.edu.uniquindio.reservasfx.controladores;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RecargarBilleteraControlador {

    @FXML
    private Button recargarBtn;

    @FXML
    private TextField recargaField;

    @FXML
    private Label saldo_Lbl;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    Sesion sesion = controlador.getSesion();

    public void inicializarValores(BilleteraVirtual billetera) {
        saldo_Lbl.setText(saldo_Lbl.getText() + billetera.getSaldo());
    }

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
