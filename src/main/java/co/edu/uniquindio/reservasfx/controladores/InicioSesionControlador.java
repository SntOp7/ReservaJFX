package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Administrador;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InicioSesionControlador {
    @FXML
    private TextField correoTxt;
    @FXML
    private Hyperlink registroHyp;
    @FXML
    private Button btnIngresarSesion;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Hyperlink recuperacionHyp;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    @FXML
    void ingresarSesionAction(ActionEvent event) {
        String correo = correoTxt.getText();
        String contrasenia = passwordTxt.getText();
        try {
            Usuario usuario = controlador.getEmpresa().getModuloUsuarioServicios().iniciarSesion(correo, contrasenia);
            if (usuario == null) {
                controlador.crearAlerta("No se encontro un usuario con ese correo y contraseña", Alert.AlertType.ERROR);
            }
            if (!usuario.isActivo()){
                controlador.crearAlerta("Es necesario activar la cuenta", Alert.AlertType.ERROR);
                controlador.navegarVentanaActivacionCuenta(btnIngresarSesion,
                        "/co/edu/uniquindio/reservasfx/activacionCuenta.fxml",
                        "Activacion de cuenta", usuario.getCedula(), usuario.getEmail());
            } else {
                controlador.getSesion().setUsuario(usuario);
                controlador.crearAlerta("Bienvenido " + usuario.getNombre(), Alert.AlertType.INFORMATION);
                if (usuario instanceof Cliente) {
                    panePrincipalControlador.actualizar("/co/edu/uniquindio/reservasfx/headerSesion.fxml",
                            "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
                } if (usuario instanceof Administrador) {
                    panePrincipalControlador.actualizar("/co/edu/uniquindio/reservasfx/headerAdministrador.fxml",
                            "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
                }
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void registroHypAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/registroCliente.fxml");
    }

    @FXML
    void recuperacionHypAction(ActionEvent event) {
        Usuario usuario = controlador.getEmpresa().getModuloUsuarioServicios()
                .getUsuarioServicios().buscarUsuarioPorEmail(correoTxt.getText());
        if (usuario != null) {
            controlador.navegarVentanaRecuperacionCuenta(recuperacionHyp,
                    "/co/edu/uniquindio/reservasfx/cambioContrasenia.fxml",
                    "Recuperar Cuenta", correoTxt.getText());
        } else {
            controlador.crearAlerta("No se encontro un usuario con el correo ingresado", Alert.AlertType.ERROR);
        }
    }
}
