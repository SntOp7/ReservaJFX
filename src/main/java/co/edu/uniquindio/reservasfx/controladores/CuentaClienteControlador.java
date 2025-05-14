package co.edu.uniquindio.reservasfx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CuentaClienteControlador {

    @FXML
    private Button billeteraBtn;

    @FXML
    private Button actualizxarDatosBtn;

    @FXML
    private Button eliminarCuentaBtn;

    @FXML
    private Label bienvenidaLbl;

    @FXML
    private Button reservasRealizadasBtn;

    @FXML
    private StackPane StckPanePrincipal;

    @FXML
    void actualizxarDatosBtnAction(ActionEvent event) {
        Parent node = cargarPanel("/co/edu/uniquindio/reservasfx/actualizacionUsuario.fxml");
        StckPanePrincipal.getChildren().setAll(node);
    }

    @FXML
    void reservasRealizadasBtnAction(ActionEvent event) {
        Parent node = cargarPanel("/co/edu/uniquindio/reservasfx/reservasRealizadas.fxml");
        StckPanePrincipal.getChildren().setAll(node);
    }

    @FXML
    void billeteraBtnAction(ActionEvent event) {
        Parent node = cargarPanel("/co/edu/uniquindio/reservasfx/recargarBilletera.fxml");
        StckPanePrincipal.getChildren().setAll(node);
    }

    @FXML
    void eliminarCuentaBtnAction(ActionEvent event) {
        Parent node = cargarPanel("/co/edu/uniquindio/reservasfx/eliminarCuenta.fxml");
        StckPanePrincipal.getChildren().setAll(node);
    }

    private Parent cargarPanel(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent node = loader.load();
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void limpiarCampos() {
        StckPanePrincipal.getChildren().clear();
    }
}
