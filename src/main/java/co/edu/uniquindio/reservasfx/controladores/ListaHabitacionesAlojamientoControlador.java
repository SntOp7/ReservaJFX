package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
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
    private Button editarHabitacionBtn1;
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

    @FXML
    void initialize() {
        habitacionesTabla = FXCollections.observableArrayList();
        initTabla();
        if (controlador.getAlojamientoSelect().getAlojamiento() != null) {
            initData();
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
            habitacionesTabla.setAll(controlador.getEmpresa().getModuloAlojamientoServicios()
                    .obtenerHabitacionesHotel(AlojamientoSelect.getInstancia().getAlojamiento().getId()));
            tableHabitacionesAgregadas.setItems(habitacionesTabla);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void agregarImagenBtnAction(ActionEvent event) {
        controlador.seleccionarImagen(imagenHabitacion);
    }

    @FXML
    void agregarHabitacionBtnAction(ActionEvent event) {
        String numeroHabitacion = numeroHabitacionTxt.getText();
        String precioAdicional = precioAdicionalTxt.getText();
        String descripcion = descripcionTxt.getText();
        String capacidad = capacidadTxt.getText();
        String urlImagen = imagenHabitacion.getImage().getUrl();
        try {
            Habitacion habitacion = controlador.getEmpresa().getModuloAlojamientoServicios().crearHabitacion(numeroHabitacion,
                    precioAdicional, capacidad, descripcion, urlImagen);
            habitacionesTabla.add(habitacion);
            tableHabitacionesAgregadas.setItems(habitacionesTabla);
            controlador.crearAlerta("Se ha agregado la habitación a la lista", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void eliminarHabitacionBtnAction(ActionEvent event) {
        Habitacion habitacion = tableHabitacionesAgregadas.getSelectionModel().getSelectedItem();
        habitacionesTabla.remove(habitacion);
        tableHabitacionesAgregadas.setItems(habitacionesTabla);
        controlador.crearAlerta("Se ha eliminado la habitación de la lista", Alert.AlertType.INFORMATION);
    }

    @FXML
    void editarHabitacionBtnAction(ActionEvent event) {
        String numeroHabitacion = numeroHabitacionTxt.getText();
        String precioAdicional = precioAdicionalTxt.getText();
        String descripcion = descripcionTxt.getText();
        String capacidad = capacidadTxt.getText();
        String urlImagen = imagenHabitacion.getImage().getUrl();
        Habitacion habitacionAntigua = tableHabitacionesAgregadas.getSelectionModel().getSelectedItem();
        try {
            controlador.getEmpresa().getModuloAlojamientoServicios().verificarEdicionHabitacion(habitacionAntigua,
                    numeroHabitacion, precioAdicional, capacidad, descripcion, urlImagen);
            tableHabitacionesAgregadas.setItems(habitacionesTabla);
            controlador.crearAlerta("Se ha editado correctamente la habitación", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
