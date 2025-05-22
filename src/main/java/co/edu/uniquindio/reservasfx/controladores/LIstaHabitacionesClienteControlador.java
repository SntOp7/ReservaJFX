package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.SeleccionReserva;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LIstaHabitacionesClienteControlador {
    @FXML
    private TableView<Habitacion> tablaHabitaciones;
    @FXML
    private TableColumn<Habitacion, Integer> numeroColumn;
    @FXML
    private TableColumn<Habitacion, Integer> capacidadColumn;
    @FXML
    private TableColumn<Habitacion, Double> precioColumn;
    @FXML
    private Label numeroHabitacionLbl;
    @FXML
    private Label descripcionLbl;
    @FXML
    private Button seleccionarBtn;

    private Habitacion habitacionSeleccionada;

    private ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    private final ObservableList<Habitacion> listaHabitaciones = FXCollections.observableArrayList();

    Alojamiento alojamiento = AlojamientoSelect.getInstancia().getAlojamiento();


    @FXML
    public void initialize() {
        initTabla();
        cargarHabitaciones();

        tablaHabitaciones.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                habitacionSeleccionada = newSel;
                numeroHabitacionLbl.setText("Hab #" + newSel.getNumero());
                descripcionLbl.setText(descripcionLbl.getText() + " " + newSel.getDescripcion());
            }
        });
    }

    private void initTabla() {
        numeroColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getNumero()).asObject());
        precioColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPrecio()).asObject());
        capacidadColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCapacidad()).asObject());
    }

    private void cargarHabitaciones() {
        try {
            String idAlojamiento = alojamiento.getId();
            ArrayList<Habitacion> habitaciones = controlador.getEmpresa().getModuloAlojamientoServicios()
                    .obtenerHabitacionesHotel(idAlojamiento);
            listaHabitaciones.addAll(habitaciones);
            tablaHabitaciones.setItems(listaHabitaciones);
        }catch(Exception e){
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void seleccionarBtnAction(ActionEvent event) {
        if (habitacionSeleccionada == null) {
            controlador.crearAlerta("Debe seleccionar una habitación", Alert.AlertType.WARNING);
            return;
        }

        SeleccionReserva.getInstancia().setHabitacionSeleccionada(habitacionSeleccionada);

        controlador.crearAlerta("Habitación seleccionada", Alert.AlertType.INFORMATION);
    }
}


