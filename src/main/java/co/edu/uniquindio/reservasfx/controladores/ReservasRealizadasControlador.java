package co.edu.uniquindio.reservasfx.controladores;


import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservasRealizadasControlador {

    @FXML
    private TableColumn<Reserva, Integer> huespedesColumn;

    @FXML
    private TableColumn<Reserva, String> fechaInicioColumn;

    @FXML
    private TableColumn<Reserva, String> fechaFinColumn;

    @FXML
    private TableColumn<Reserva, Double> totalPagadoColumn;

    @FXML
    private TableColumn<Reserva, EstadoReserva> estadoColumn;

    @FXML
    private TableView<Reserva> tablaReservas;

    @FXML
    private TableColumn<Reserva, String> nombreColumn;

    @FXML
    private Button cancelarReservaBtn;

    private final ObservableList<Reserva> listaReservas = javafx.collections.FXCollections.observableArrayList();

    private final ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    private Sesion sesion;

    public void initialize(URL location, ResourceBundle resources) {
        initTabla();
        initData();
        tablaReservas.setItems(listaReservas);
    }

    /**
     * Método que se encarga de inicializar la informacion de la tabla
     */
    public void initTabla() {
        nombreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdAlojamiento()));
        fechaInicioColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaInicio().toString()));
        fechaFinColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaInicio().toString()));
        huespedesColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getNumeroHuespedes()).asObject());
        totalPagadoColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getFactura().getTotal()).asObject());
        estadoColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEstado()));
    }

    public void initData(){
        try {
            String cedula = sesion.getUsuario().getCedula();
            ArrayList<Reserva> reservas = controlador.getEmpresa().getModuloComercialServicios().obtenerReservasCliente(cedula);
            listaReservas.setAll(reservas);
        }catch (Exception e){
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);

        }
    }

    @FXML
    void cancelarReservaBtnAction(ActionEvent event) {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada == null) {
            controlador.crearAlerta("Debe seleccionar una reserva para cancelar.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar cancelación");
        confirmacion.setHeaderText("¿Estás seguro de que deseas cancelar esta reserva?");
        confirmacion.setContentText("Reserva: " + reservaSeleccionada.getId());

        if (confirmacion.showAndWait().get() != javafx.scene.control.ButtonType.OK) {
            return;
        }

        try {
            controlador.getEmpresa().getModuloComercialServicios().cancelarReserva(reservaSeleccionada.getId());
            listaReservas.remove(reservaSeleccionada);
            controlador.crearAlerta("Reserva cancelada exitosamente.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controlador.crearAlerta("Error al cancelar la reserva: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
