package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.Mes;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.SimpleStyleableDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Objects;

public class PanelAdministracionControlador {
    @FXML
    private TableColumn<TipoServicio, String> tipoServicioColumn;
    @FXML
    private TextField descripcionField;
    @FXML
    private Button registrarAlojamientoBtn;
    @FXML
    private ComboBox<String> ciudadBox;
    @FXML
    private TextField precioNocheField;
    @FXML
    private Button agregarImagenesBtn;
    @FXML
    private TableColumn<EstadisticasTipoAlojamiento, String> tipoAlojamientoColumn;
    @FXML
    private TableView<EstadisticasTipoAlojamiento> tablaEstadisticas;
    @FXML
    private TextField capacidadField;
    @FXML
    private TextField nombreField;
    @FXML
    private Button imagenPrincipalBtn;
    @FXML
    private TableView<TipoServicio> tablaTipoServicios;
    @FXML
    private ComboBox<String> tipoAlojamientoBox;
    @FXML
    private TableColumn<EstadisticasTipoAlojamiento, Double> gananciasTotalesColumn;
    @FXML
    private TitledPane accordionRentabilidad;
    @FXML
    private ComboBox<String> seleccionarMesBox;
    @FXML
    private StackPane tipoAlojamientoStack;
    @FXML
    private ImageView imagenSecundaria3;
    @FXML
    private ImageView imagenSecundaria2;
    @FXML
    private ImageView imagenSecundaria1;
    @FXML
    private ImageView imagenPrincipal;
    @FXML
    private StackPane stackpaneGraficos;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();
    CostoAdicionalAlojamientoControlador costoAdicionalAlojamientoControlador;
    ListaHabitacionesAlojamientoControlador listaHabitacionesAlojamientoControlador;

    ObservableList<TipoServicio> tipoServicios;
    ObservableList<EstadisticasTipoAlojamiento> estadisticasTipoAlojamiento;

    private String rutaImagenPrincipal;
    private String rutaImagenSecundaria1;
    private String rutaImagenSecundaria2;
    private String rutaImagenSecundaria3;

    @FXML
    void initialize() {
        estadisticasTipoAlojamiento = FXCollections.observableArrayList();
        tipoServicios = FXCollections.observableArrayList();
        tablaTipoServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        controlador.getAlojamientoSelect().reiniciar();

        initTablaServicios();
        initTablaRentabilidad();
        cargarOpcionesTipoAlojamiento();
        cargarOpcionesCiudad();
        cargarOpcionesMes();
        cargarTablaServicios();
        agregarListener();
    }

    private void agregarListener() {
        tipoAlojamientoBox.valueProperty().addListener((obs, oldVal, newVal)
                -> cargarDatosAdicionales());
    }

    private void cargarDatosAdicionales() {
        String tipoAlojamiento = tipoAlojamientoBox.getSelectionModel().getSelectedItem();
        if (tipoAlojamiento == null) return;
        switch (tipoAlojamiento) {
            case "Casa", "Apartamento" -> cargarStackPane();
            case "Hotel" -> cargarStackPaneHotel();
        }
    }

    private void cargarStackPane() {
        tipoAlojamientoStack.getChildren().clear();
        Parent node = cargarPanel();
        tipoAlojamientoStack.getChildren().add(node);
    }

    private void cargarStackPaneHotel() {
        tipoAlojamientoStack.getChildren().clear();
        Parent node = cargarPanelHotel();
        tipoAlojamientoStack.getChildren().add(node);
    }

    private void initTablaServicios() {
        tipoServicioColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
    }

    private void initTablaRentabilidad() {
        tipoAlojamientoColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipo().getNombre()));
        gananciasTotalesColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getRentabilidadTipo()));
    }

    private void cargarOpcionesTipoAlojamiento() {
        TipoAlojamiento[] tiposAlojamiento = TipoAlojamiento.values();
        for (TipoAlojamiento tipoAlojamiento : tiposAlojamiento) {
            tipoAlojamientoBox.getItems().add(tipoAlojamiento.getNombre());
        }
    }

    private void cargarOpcionesCiudad() {
        Ciudad[] ciudades = Ciudad.values();
        for (Ciudad ciudad : ciudades) {
            ciudadBox.getItems().add(ciudad.getNombre());
        }
    }

    private void cargarOpcionesMes() {
        Mes[] meses = Mes.values();
        for (Mes mes : meses) {
            seleccionarMesBox.getItems().add(mes.getNombre());
        }
    }

    private void cargarTablaServicios() {
        TipoServicio[] servicios = TipoServicio.values();
        tipoServicios.addAll(servicios);
        tablaTipoServicios.setItems(tipoServicios);
    }

    @FXML
    void seleccionarMesBoxAction(ActionEvent event) {
        int mes = seleccionarMesBox.getSelectionModel().getSelectedIndex() + 1;
        cargarTablaRentabilidad(mes);
        cargarGraficoRentabilidad();
    }

    private void cargarTablaRentabilidad(int mes) {
        try {
            ArrayList<EstadisticasTipoAlojamiento> estadisticas = controlador.getEmpresa().
                    getModuloComercialServicios().obtenerRentabilidadTipoAlojamiento(mes);
            estadisticasTipoAlojamiento.clear();
            estadisticasTipoAlojamiento.addAll(estadisticas);
            tablaEstadisticas.setItems(estadisticasTipoAlojamiento);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarGraficoRentabilidad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/co/edu/uniquindio/reservasfx/graficas.fxml"));
            Parent graficoNode = loader.load();

            GraficasControlador graficasControlador = loader.getController();
            graficasControlador.graficarTipos(new ArrayList<>(estadisticasTipoAlojamiento));

            stackpaneGraficos.getChildren().clear();
            stackpaneGraficos.getChildren().add(graficoNode);

        } catch (Exception e) {
            controlador.crearAlerta("Error al cargar el gráfico: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void imagenPrincipalBtnAction(ActionEvent event) {
        rutaImagenPrincipal = controlador.seleccionarImagen(imagenPrincipal);
    }

    @FXML
    void agregarImagenesBtnAction(ActionEvent event) {
        rutaImagenSecundaria1 = controlador.seleccionarImagen(imagenSecundaria1);
        rutaImagenSecundaria2 = controlador.seleccionarImagen((imagenSecundaria2));
        rutaImagenSecundaria3 = controlador.seleccionarImagen((imagenSecundaria3));
    }

    @FXML
    void registrarAlojamientoBtn(ActionEvent event) {
        String urlPrincipal = rutaImagenPrincipal == null ? null : rutaImagenPrincipal;
        String urlSecundaria1 = rutaImagenSecundaria1 == null ? null : rutaImagenSecundaria1;
        String urlSecundaria2 = rutaImagenSecundaria2 == null ? null : rutaImagenSecundaria2;
        String urlSecundaria3 = rutaImagenSecundaria3 == null ? null : rutaImagenSecundaria3;
        ArrayList<String> imagenes = new ArrayList<>();
        imagenes.add(urlSecundaria1);
        imagenes.add(urlSecundaria2);
        imagenes.add(urlSecundaria3);
        String tipoAlojamientoString = tipoAlojamientoBox.getSelectionModel().getSelectedItem();
        if (tipoAlojamientoString == null) {
            controlador.crearAlerta("Debe seleccionar primero un tipo de alojamiento.", Alert.AlertType.ERROR);
            return;
        }
        TipoAlojamiento tipoAlojamiento = TipoAlojamiento.valueOf(tipoAlojamientoString.toUpperCase());
        String nombre = nombreField.getText();
        String ciudad = ciudadBox.getSelectionModel().getSelectedItem();
        String descripcion = descripcionField.getText();
        String precioNoche = precioNocheField.getText();
        String capacidad = capacidadField.getText();
        ObservableList<TipoServicio> serviciosSeleccionados = tablaTipoServicios.getSelectionModel().getSelectedItems();
        ArrayList<TipoServicio> servicios = new ArrayList<>(serviciosSeleccionados);
        try {
            if (tipoAlojamiento.equals(TipoAlojamiento.HOTEL)) {
                controlador.getEmpresa().getModuloAlojamientoServicios().registrarAlojamiento(tipoAlojamiento, nombre,
                        ciudad, descripcion, precioNoche, capacidad, servicios, urlPrincipal, imagenes,
                        null, listaHabitacionesAlojamientoControlador.habitaciones);
            } else if (tipoAlojamiento.equals(TipoAlojamiento.APARTAMENTO) || tipoAlojamiento.equals(TipoAlojamiento.CASA)) {
                controlador.getEmpresa().getModuloAlojamientoServicios().registrarAlojamiento(tipoAlojamiento, nombre,
                        ciudad, descripcion, precioNoche, capacidad, servicios, urlPrincipal, imagenes,
                        costoAdicionalAlojamientoControlador.costoAdicional, null);
            }
            controlador.crearAlerta("Alojamiento registrado exitosamente.", Alert.AlertType.INFORMATION);
            panePrincipalControlador.actualizarInferior(
                    "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Parent cargarPanel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/co/edu/uniquindio/reservasfx/costoAdicionalAlojamiento.fxml"));
            Parent node = loader.load();
            costoAdicionalAlojamientoControlador = loader.getController();
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Parent cargarPanelHotel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/co/edu/uniquindio/reservasfx/listaHabitacionesAlojamiento.fxml"));
            Parent node = loader.load();
            listaHabitacionesAlojamientoControlador = loader.getController();
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
