package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReseniaClienteControlador {

    @FXML
    private Label estrellasLbl;

    @FXML
    private Label nombreLbl;

    @FXML
    private Label comentariosLbl;

    public void inicializarValores(Calificacion calificacion) {
        nombreLbl.setText(calificacion.getCliente().getNombre()); // o lo que uses para nombre
        estrellasLbl.setText(String.valueOf(calificacion.getValoracion()));
        comentariosLbl.setText(calificacion.getComentario());
    }
}
