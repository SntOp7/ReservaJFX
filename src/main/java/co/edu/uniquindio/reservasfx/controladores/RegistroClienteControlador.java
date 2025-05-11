package co.edu.uniquindio.reservasfx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistroClienteControlador {
    @FXML
    private TextField correoTxt;
    @FXML
    private Button registrarseButton;
    @FXML
    private TextField nombreTxt;
    @FXML
    private TextField cedulaTxt;
    @FXML
    private TextField telefonoTxt;
    @FXML
    private Button cancelarBtn;
    @FXML
    private PasswordField contraseniaField;
    @FXML
    private Hyperlink iniciarSesionLink;
    @FXML
    private TextField direccionTxt;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    @FXML
    void registrarseAction(ActionEvent event) {
        String nombre = nombreTxt.getText();
        String cedula = cedulaTxt.getText();
        String telefono = telefonoTxt.getText();
        String correo = correoTxt.getText();
        String contrasenia = contraseniaField.getText();
        String direccion = direccionTxt.getText();

        try {
            controlador.getEmpresa().registrarCliente(cedula, nombre, telefono, direccion, correo, contrasenia, false);
            controlador.crearAlerta("Usuario registrado con exito", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cancelarAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
    }

    @FXML
    void iniciarSesionLinkAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/inicioSesion.fxml");
    }
}
