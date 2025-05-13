package co.edu.uniquindio.reservasfx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Cliente2Controlador {

    @FXML
    private Button seleccionarBtn;

    @FXML
    private Label numeroHabitacionLbl;

    @FXML
    private Label descripcionLbl;

    @FXML
    private TableView<?> tablaHabitaciones;

    @FXML
    private TableColumn<?, ?> nombreColumn;

    @FXML
    private TableColumn<?, ?> capacidadColumn;

    @FXML
    private TableColumn<?, ?> precioColumn;

    @FXML
    void seleccionarBtnAction(ActionEvent event) {

    }
}
