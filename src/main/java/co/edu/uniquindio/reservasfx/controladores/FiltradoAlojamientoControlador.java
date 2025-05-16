package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class FiltradoAlojamientoControlador {
    @FXML
    private Label titulolbl;
    @FXML
    private TextField maxTxt;
    @FXML
    private ComboBox<String> ciudadCombo;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField nombreTxt;
    @FXML
    private StackPane sextoStack;
    @FXML
    private StackPane primerStack;
    @FXML
    private RadioButton listaDeseosRadioBtn;
    @FXML
    private StackPane cuartoStack;
    @FXML
    private StackPane segundoStack;
    @FXML
    private Button anteriorBtn;
    @FXML
    private StackPane tercerStack;
    @FXML
    private Button siguienteBtn;
    @FXML
    private Button filtrarBtn;
    @FXML
    private StackPane quintoStack;
    @FXML
    private Label numeroPaginalbl;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    int paginaActual;
    int totalPaginas;

    @FXML
    void initialize() {
        Ciudad[] ciudades = Ciudad.values();
        for (Ciudad ciudad : ciudades) {
            ciudadCombo.getItems().add(ciudad.getNombre());
        }
    }

    @FXML
    void anteriorAction(ActionEvent event) {

    }

    @FXML
    void siguienteAction(ActionEvent event) {

    }

    @FXML
    void filtrarAction(ActionEvent event) {

    }

    @FXML
    void listaDeseosAction(ActionEvent event) {

    }

    public void inicializarValores(String titulo) {
        titulolbl.setText(titulo);
    }
}
