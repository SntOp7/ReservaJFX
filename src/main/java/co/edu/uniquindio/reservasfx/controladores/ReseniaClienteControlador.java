package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Comparator;

public class ReseniaClienteControlador {

    @FXML
    private Label estrellasLbl;

    @FXML
    private Label nombreLbl;

    @FXML
    private Label comentariosLbl;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    private ArrayList<Cliente> clientes = controlador.getEmpresa().getModuloUsuarioServicios().getUsuarioServicios().obtenerClientes();

    public void inicializarValores(Calificacion calificacion) {
        String cedula = calificacion.getCedulaCliente();
        String nombre = clientes.stream().filter(a -> a.equals(cedula)).findFirst().get().getNombre();
        nombreLbl.setText(nombre);
        estrellasLbl.setText(String.valueOf(calificacion.getValoracion()));
        comentariosLbl.setText(calificacion.getComentario());
    }
}
