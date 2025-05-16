package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
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

    final static int MAX_ALOJAMIENTOS_POR_PAGINA = 6;
    StackPane[] stacks;

    ArrayList<Alojamiento> alojamientosFiltrados;
    ArrayList<Alojamiento> alojamientosDeseos;
    ArrayList<Alojamiento> alojamientosPagina;

    TipoAlojamiento tipoAlojamiento;
    int paginaActual;
    int totalPaginas;

    @FXML
    void initialize() {
        stacks = new StackPane[]{
                primerStack, segundoStack, tercerStack, cuartoStack, quintoStack, sextoStack
        };

        paginaActual = 1;
        Ciudad[] ciudades = Ciudad.values();
        for (Ciudad ciudad : ciudades) {
            ciudadCombo.getItems().add(ciudad.getNombre());
        }
        agregarListenersFiltros();
    }

    private void agregarListenersFiltros() {
        nombreTxt.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        ciudadCombo.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        minTxt.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        maxTxt.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
    }

    private void aplicarFiltros() {
        try {
            String nombre = nombreTxt.getText().trim();
            String ciudadSeleccionada = ciudadCombo.getValue();
            String precioMin = minTxt.getText();
            String precioMax = maxTxt.getText();

            alojamientosFiltrados = controlador.getEmpresa().getModuloAlojamientoServicios()
                    .obtenerAlojamientosPorFiltro(tipoAlojamiento, nombre, ciudadSeleccionada, precioMin, precioMax);
            cargarDatosPanelConAlojamientos(alojamientosFiltrados);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosPanelConAlojamientos(ArrayList<Alojamiento> alojamientos) {
        totalPaginas = alojamientos.size() / MAX_ALOJAMIENTOS_POR_PAGINA;
        totalPaginas = totalPaginas + (alojamientos.size() % MAX_ALOJAMIENTOS_POR_PAGINA == 0 ? 0 : 1);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
        alojamientosPagina = controlador.determinarAlojamientosPagina(paginaActual, MAX_ALOJAMIENTOS_POR_PAGINA, alojamientos);
        controlador.cargarListaAlojamientos(alojamientosPagina, stacks);
    }

    @FXML
    void anteriorAction(ActionEvent event) {
        if (paginaActual == 1) return;

        paginaActual--;
        alojamientosPagina = controlador.determinarAlojamientosPagina(paginaActual, MAX_ALOJAMIENTOS_POR_PAGINA, alojamientosFiltrados);
        controlador.cargarListaAlojamientos(alojamientosPagina, stacks);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    @FXML
    void siguienteAction(ActionEvent event) {
        if (paginaActual == totalPaginas) return;

        paginaActual++;
        alojamientosPagina = controlador.determinarAlojamientosPagina(paginaActual, MAX_ALOJAMIENTOS_POR_PAGINA, alojamientosFiltrados);
        controlador.cargarListaAlojamientos(alojamientosPagina, stacks);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    @FXML
    void listaDeseosAction(ActionEvent event) {
        try {
            if (listaDeseosRadioBtn.isSelected()) {
                ArrayList<Deseo> deseosCliente = controlador.getEmpresa().getModuloUsuarioServicios()
                        .obtenerDeseosCliente(controlador.getSesion().getUsuario().getCedula());
                alojamientosDeseos = controlador.getEmpresa().getModuloAlojamientoServicios()
                        .obtenerAlojamientosPorDeseosCliente(deseosCliente);
                cargarDatosPanelConAlojamientos(alojamientosDeseos);
            } else {
                limpiarAlojamientos();
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void limpiarAlojamientos() {
        for (StackPane stackPane : stacks) {
            stackPane.getChildren().clear();
        }
    }

    public void inicializarValores(String titulo, TipoAlojamiento tipoAlojamiento) {
        this.tipoAlojamiento = tipoAlojamiento;
        titulolbl.setText(titulo);
    }
}
