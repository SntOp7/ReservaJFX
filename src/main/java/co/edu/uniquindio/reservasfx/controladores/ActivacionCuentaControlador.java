package co.edu.uniquindio.reservasfx.controladores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ActivacionCuentaControlador {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField codeTextField;

    @FXML
    private Button acceptButton;

    String cedula;
    String correo;
    String codigoCorrecto;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    @FXML
    void initialize(){
        try {
            codigoCorrecto = controlador.getEmpresa().getModuloUsuarioServicios().enviarCodigo(correo, false);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void aceptarBtnAction(ActionEvent event) {
        try{
            String codigoIng = codeTextField.getText();
            controlador.getEmpresa().getModuloUsuarioServicios().activarCuentaCliente(cedula, codigoCorrecto, codigoIng);
        }catch (Exception e){
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cancelarBtnAction(ActionEvent event) {
        controlador.cerrarVentana(cancelButton);
    }

    public void inicializarValores(String cedulaUs, String correoUs) {
        this.cedula = cedulaUs;
        this.correo = correoUs;
    }

}
