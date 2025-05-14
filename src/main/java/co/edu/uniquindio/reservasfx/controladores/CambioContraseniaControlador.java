package co.edu.uniquindio.reservasfx.controladores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CambioContraseniaControlador {

    @FXML
    private TextField codigoField;

    @FXML
    private PasswordField repetirContraseniaField;

    @FXML
    private PasswordField ContraseniaField;

    @FXML
    private Button cancelarBtn;

    @FXML
    private Button aceptarBtn;

    String correo;
    String codigoCorrecto;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    @FXML
    void initialize(){
        try {
            codigoCorrecto = controlador.getEmpresa().getModuloUsuarioServicios().enviarCodigo(correo, true);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void aceptarBtnAction(ActionEvent event) throws Exception {
        String codigoIngresado = codigoField.getText();
        String contraseniaNueva = ContraseniaField.getText();
        String contraseniaRep = repetirContraseniaField.getText();
        controlador.getEmpresa().getModuloUsuarioServicios().cambiarContrasenia(correo, codigoCorrecto, codigoIngresado, contraseniaNueva, contraseniaRep);
    }

    @FXML
    void cancelarBtnAction(ActionEvent event) {
        controlador.cerrarVentana(cancelarBtn);
    }

    public void inicializarValores(String correoUs) throws Exception {
        this.correo = correoUs;
    }


}
