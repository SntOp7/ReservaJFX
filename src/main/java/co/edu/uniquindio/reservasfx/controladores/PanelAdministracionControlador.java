package co.edu.uniquindio.reservasfx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class PanelAdministracionControlador {
    @FXML
    private TableColumn<?, ?> tipoServicioColumn;
    @FXML
    private TextField descripcionField;
    @FXML
    private Button registrarAlojamientoBtn;
    @FXML
    private ComboBox<?> ciudadBox;
    @FXML
    private TextField precioNocheField;
    @FXML
    private Button agregarImagenesBtn;
    @FXML
    private TableColumn<?, ?> tipoAlojamientoColumn;
    @FXML
    private TableView<?> tablaEstadisticas;
    @FXML
    private TextField capacidadField;
    @FXML
    private TextField nombreField;
    @FXML
    private Button imagenPrincipalBtn;
    @FXML
    private TableView<?> tablaTipoServicios;
    @FXML
    private ComboBox<?> tipoAlojamientoBox;
    @FXML
    private BarChart<?, ?> graficoBarrasAlojamiento;
    @FXML
    private TableColumn<?, ?> gananciasTotalesColumn;
    @FXML
    private TitledPane accordionRentabilidad;

    @FXML
    void imagenPrincipalBtnAction(ActionEvent event) {

    }

    @FXML
    void agregarImagenesBtnAction(ActionEvent event) {

    }

    @FXML
    void registrarAlojamientoBtn(ActionEvent event) {

    }
}
