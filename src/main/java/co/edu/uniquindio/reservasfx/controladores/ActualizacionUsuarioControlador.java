package co.edu.uniquindio.reservasfx.controladores;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Objects;

public class ActualizacionUsuarioControlador {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button cambioContraseniaBtn;

    @FXML
    private Button actualizarBtn;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField contraseniaField;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();
    Sesion sesion = controlador.getSesion();
    String cedula;

    @FXML
    public void initialize() {
        Usuario usuarioSesion = sesion.getUsuario();

        if (usuarioSesion instanceof Cliente cliente) {
            txtCedula.setText(cliente.getCedula());
            txtNombre.setText(cliente.getNombre());
            txtCorreo.setText(cliente.getEmail());
            txtDireccion.setText(cliente.getDireccion());
            txtTelefono.setText(cliente.getTelefono());
        }
    }

    @FXML
    void actualizarAction(ActionEvent event){
        try {
            String cedulaSesion = sesion.getUsuario().getCedula();
            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String direccion = txtDireccion.getText();
            String telefono = txtTelefono.getText();
            String contrasenia = contraseniaField.getText();

            Cliente cliente = controlador.getEmpresa().getModuloUsuarioServicios().getUsuarioServicios().buscarClientePorCedula(cedulaSesion);

            if(Objects.equals(contrasenia, cliente.getContrasenia())){
                controlador.getEmpresa().getModuloUsuarioServicios().editarCliente(cliente, cedula, nombre, telefono, direccion, correo);
            } else {
                controlador.crearAlerta("La contraseña es incorrecta", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cambioContraseniaAction(ActionEvent event) {
        controlador.navegarVentana(cambioContraseniaBtn, "/co/edu/uniquindio/reservasfx/cambioContrasenia.fxml", "Cambiar Contrasenia");
    }

}
