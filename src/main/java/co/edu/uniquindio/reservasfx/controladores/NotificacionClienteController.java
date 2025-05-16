package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NotificacionClienteController {

    @FXML
    private Label tituloLbl;

    @FXML
    private Label asuntoLbl;

    public void inicializarValores(Notificacion notificacion) {
        tituloLbl.setText(notificacion.getTitulo());
        asuntoLbl.setText(notificacion.getMensaje());
    }
}
