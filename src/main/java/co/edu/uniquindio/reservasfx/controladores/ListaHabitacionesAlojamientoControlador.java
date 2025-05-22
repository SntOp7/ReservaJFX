package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ListaHabitacionesAlojamientoControlador {
    @FXML
    private TableView<Habitacion> tableHabitacionesAgregadas;
    @FXML
    private TextField descripcionTxt;
    @FXML
    private TextField numeroHabitacionTxt;
    @FXML
    private Button eliminarHabitacionBtn;
    @FXML
    private Button agregarImagenBtn;
    @FXML
    private Button editarHabitacionBtn;
    @FXML
    private TextField capacidadTxt;
    @FXML
    private TextField precioAdicionalTxt;
    @FXML
    private TableColumn<Habitacion, Integer> numeroHabitacionColumn;
    @FXML
    private Button agregarHabitacionBtn;
    @FXML
    private TableColumn<Habitacion, Double> precioAdicionalColumn;
    @FXML
    private TableColumn<Habitacion, Integer> capacidadColumn;
    @FXML
    private ImageView imagenHabitacion;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    ObservableList<Habitacion> habitacionesTabla;
    ArrayList<Habitacion> habitaciones = new ArrayList<>();

    private Habitacion habitacionSelected;

    private String rutaImagen;

    @FXML
    void initialize() {
        habitacionesTabla = FXCollections.observableArrayList();
        initTabla();
        if (controlador.getAlojamientoSelect().getAlojamiento() != null) {
            initData();
        }
        seleccionarHabitacion();
    }

    private void seleccionarHabitacion() {
        tableHabitacionesAgregadas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            habitacionSelected = newSelection;
            cargarDatos();
        });
    }

    private void cargarDatos() {
        if (habitacionSelected != null) {
            rutaImagen = habitacionSelected.getImagen();
            controlador.cargarImagen(rutaImagen, imagenHabitacion);
            numeroHabitacionTxt.setText(habitacionSelected.getNumero() + "");
            precioAdicionalTxt.setText(habitacionSelected.getPrecio() + "");
            capacidadTxt.setText(habitacionSelected.getCapacidad() + "");
            descripcionTxt.setText(habitacionSelected.getDescripcion());
        }
    }

    private void initTabla() {
        numeroHabitacionColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getNumero()).asObject());
        precioAdicionalColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPrecio()).asObject());
        capacidadColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCapacidad()).asObject());
    }

    private void initData() {
        try {
            habitaciones = controlador.getEmpresa().getModuloAlojamientoServicios()
                    .obtenerHabitacionesHotel(AlojamientoSelect.getInstancia().getAlojamiento().getId());
            habitacionesTabla.setAll(habitaciones);
            tableHabitacionesAgregadas.setItems(habitacionesTabla);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void agregarImagenBtnAction(ActionEvent event) {
        rutaImagen = controlador.seleccionarImagen(imagenHabitacion);
    }

    @FXML
    void agregarHabitacionBtnAction(ActionEvent event) {
        String numeroHabitacion = numeroHabitacionTxt.getText();
        String precioAdicional = precioAdicionalTxt.getText();
        String descripcion = descripcionTxt.getText();
        String capacidad = capacidadTxt.getText();
        String urlImagen = rutaImagen == null ? null : rutaImagen.isEmpty() ? null : rutaImagen;
        try {
            Alojamiento alojamiento = controlador.getAlojamientoSelect().getAlojamiento();
            Habitacion habitacion = controlador.getEmpresa().getModuloAlojamientoServicios().crearHabitacion(numeroHabitacion,
                    precioAdicional, capacidad, descripcion, urlImagen);
            if (alojamiento != null) {
                controlador.getEmpresa().getModuloAlojamientoServicios().registrarHabitacion(alojamiento.getId(), habitacion);
            }
            habitaciones.add(habitacion);
            habitacionesTabla.setAll(habitaciones);
            tableHabitacionesAgregadas.setItems(habitacionesTabla);
            controlador.crearAlerta("Se ha agregado la habitación a la lista", Alert.AlertType.INFORMATION);
            tableHabitacionesAgregadas.getSelectionModel().clearSelection();
            limpiarCampos();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void eliminarHabitacionBtnAction(ActionEvent event) {
        Habitacion habitacion = tableHabitacionesAgregadas.getSelectionModel().getSelectedItem();
        if (habitacion == null) {
            controlador.crearAlerta("Debes seleccionar una habitación", Alert.AlertType.WARNING);
            return;
        }
        try {
            String idAlojamiento = controlador.getAlojamientoSelect().getAlojamiento().getId();
            controlador.getEmpresa().getModuloAlojamientoServicios().eliminarHabitacion(idAlojamiento, habitacion.getNumero());
            habitaciones = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerHabitacionesHotel(idAlojamiento);
            habitacionesTabla.setAll(habitaciones);
            tableHabitacionesAgregadas.setItems(habitacionesTabla);
            controlador.crearAlerta("Se ha eliminado la habitación de la lista", Alert.AlertType.INFORMATION);
            tableHabitacionesAgregadas.getSelectionModel().clearSelection();
            limpiarCampos();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void editarHabitacionBtnAction(ActionEvent event) {
        Habitacion habitacionAntigua = tableHabitacionesAgregadas.getSelectionModel().getSelectedItem();
        if (habitacionAntigua == null) {
            controlador.crearAlerta("Debes seleccionar una habitación", Alert.AlertType.WARNING);
            return;
        }
        String numeroHabitacion = numeroHabitacionTxt.getText();
        String precioAdicional = precioAdicionalTxt.getText();
        String descripcion = descripcionTxt.getText();
        String capacidad = capacidadTxt.getText();
        String urlImagen = imagenHabitacion.getImage() == null ? null : imagenHabitacion.getImage().getUrl();
        try {
            Habitacion habitacionEditada = controlador.getEmpresa().getModuloAlojamientoServicios()
                    .verificarEdicionHabitacion(habitacionAntigua, numeroHabitacion, precioAdicional,
                            capacidad, descripcion, urlImagen, habitaciones);
            habitaciones.remove(habitacionAntigua);
            habitaciones.add(habitacionEditada);
            habitacionesTabla.setAll(habitaciones);
            tableHabitacionesAgregadas.setItems(habitacionesTabla);
            controlador.crearAlerta("Se ha editado correctamente la habitación", Alert.AlertType.INFORMATION);
            tableHabitacionesAgregadas.getSelectionModel().clearSelection();
            limpiarCampos();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        numeroHabitacionTxt.clear();
        precioAdicionalTxt.clear();
        descripcionTxt.clear();
        capacidadTxt.clear();
        imagenHabitacion.setImage(null);
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return Objects.requireNonNullElseGet(habitaciones, ArrayList::new);
    }
}
