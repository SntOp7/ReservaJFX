package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class RecomendadoAlojamientoControlador {
    @FXML
    private StackPane cuartoStack;
    @FXML
    private Button paginaAnteriorBtn;
    @FXML
    private StackPane segundoStack;
    @FXML
    private StackPane decimoStack;
    @FXML
    private StackPane tercerStack;
    @FXML
    private StackPane sextoStack;
    @FXML
    private StackPane novenoStack;
    @FXML
    private StackPane octavoStack;
    @FXML
    private StackPane primerStack;
    @FXML
    private StackPane septimoStack;
    @FXML
    private Button siguientePaginaBtn;
    @FXML
    private StackPane quintoStack;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    @FXML
    void initialize() {
        try {
            ArrayList<Alojamiento> alojamientos = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerAlojamientosAleatorios();
            for (Alojamiento a : alojamientos) {
                cargarAlojamiento("/co/edu/uniquindio/reservasfx/alojamiento.fxml", cuartoStack);
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void cargarAlojamiento(String rutaFXML, StackPane stack) {
        Parent node = controlador.cargarPanel(rutaFXML);
        stack.getChildren().setAll(node);
    }

    @FXML
    void paginaAnteriorAction(ActionEvent event) {

    }

    @FXML
    void siguientePaginaAction(ActionEvent event) {

    }
}
