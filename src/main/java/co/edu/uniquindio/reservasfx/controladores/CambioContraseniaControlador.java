package co.edu.uniquindio.reservasfx.controladores;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
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
    Sesion sesion = controlador.getSesion();

    @FXML
    void initialize(){
        try {
            if (sesion.getUsuario() != null){
                correo = sesion.getUsuario().getEmail();
                codigoCorrecto = controlador.getEmpresa().getModuloUsuarioServicios().enviarCodigo(correo, true);
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void aceptarBtnAction(ActionEvent event){
        try{
            String codigoIngresado = codigoField.getText();
            String contraseniaNueva = ContraseniaField.getText();
            String contraseniaRep = repetirContraseniaField.getText();
            controlador.getEmpresa().getModuloUsuarioServicios().cambiarContrasenia(correo, codigoCorrecto, codigoIngresado, contraseniaNueva, contraseniaRep);
            controlador.crearAlerta("Se ha cambiado la contraseña correctamente", Alert.AlertType.INFORMATION);
            controlador.cerrarVentana(aceptarBtn);
        } catch (Exception e){
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    void cancelarBtnAction(ActionEvent event) {
        controlador.cerrarVentana(cancelarBtn);
    }

    public void inicializarValores(String correoUs) {
        try {
            correo = correoUs;
            codigoCorrecto = controlador.getEmpresa().getModuloUsuarioServicios().enviarCodigo(correo, true);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
