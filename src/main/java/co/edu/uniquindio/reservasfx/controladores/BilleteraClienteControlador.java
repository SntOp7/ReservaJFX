package co.edu.uniquindio.reservasfx.controladores;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.UUID;

public class BilleteraClienteControlador {

    @FXML
    private Button recargarBtn;

    @FXML
    private TextField recargaField;

    @FXML
    private Label saldo_Lbl;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    Sesion sesion = controlador.getSesion();

    @FXML
    public void initialize() {
        inicializarValores();
    }

    public void inicializarValores() {
        String cedulaSesion = sesion.getUsuario().getCedula();
        Cliente cliente = controlador.getEmpresa().getModuloUsuarioServicios().getUsuarioServicios().buscarClientePorCedula(cedulaSesion);

        if (cliente == null) {
            controlador.crearAlerta("No se encontró el cliente en la sesión actual.", Alert.AlertType.ERROR);
            return;
        }

        if (cliente.getBilletera() == null) {
            cliente.setBilletera(new BilleteraVirtual(UUID.randomUUID().toString(), 0));
        }

        saldo_Lbl.setText("Saldo Disponible: " + cliente.getBilletera().getSaldo());
    }

    @FXML
    void recargarBtnAction(ActionEvent event) {
        try {
            String contenido = recargaField.getText().trim();
            if (contenido == null || contenido.equals("")) throw new Exception("El monto no puede estar vacio");
            double monto = Double.parseDouble(contenido);
            String cedula = sesion.getUsuario().getCedula();
            controlador.getEmpresa().getModuloUsuarioServicios().recargarBilleteraCliente(cedula, monto);
            Cliente clienteActualizado = controlador.getEmpresa()
                    .getModuloUsuarioServicios()
                    .getUsuarioServicios()
                    .buscarClientePorCedula(cedula);
            saldo_Lbl.setText("Saldo Disponible: " + clienteActualizado.getBilletera().getSaldo());
            recargaField.clear();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
