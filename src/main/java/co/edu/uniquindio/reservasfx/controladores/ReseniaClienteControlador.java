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

    public void inicializarValores(Calificacion calificacion) {
        Cliente cliente = controlador.getEmpresa().getModuloUsuarioServicios().getUsuarioServicios()
                .buscarClientePorCedula(calificacion.getCedulaCliente());
        String nombre = cliente.getNombre();
        nombreLbl.setText(nombre);
        estrellasLbl.setText(calificacion.getValoracion() + "");
        comentariosLbl.setText(calificacion.getComentario());
    }
}
