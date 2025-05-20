package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class NotificacionClienteController {

    @FXML
    private Label tituloLbl;

    @FXML
    private Label asuntoLbl;

    @FXML
    private Button marcarLeidoBtn;

    Notificacion notificacionActual;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    public void inicializarValores(Notificacion notificacion) {
        tituloLbl.setText(notificacion.getTitulo());
        asuntoLbl.setText(notificacion.getMensaje());
        this.notificacionActual = notificacion;
    }

    @FXML
    void marcarLeidoBtnAction(ActionEvent event) {
        try{
            controlador.getEmpresa().getModuloUsuarioServicios().marcarNotificacionComoLeida(notificacionActual);
            controlador.crearAlerta("Se marco como leida correctamente",  Alert.AlertType.INFORMATION);
        }catch(Exception e){
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
