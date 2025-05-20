package co.edu.uniquindio.reservasfx.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Getter;

public class CostoAdicionalAlojamientoControlador {
    @FXML
    private TextField costoAdicionalTxt;

    @Getter
    String costoAdicional;

    @FXML
    void initialize() {
        agregarListener();
    }

    private void agregarListener() {
        costoAdicionalTxt.textProperty().addListener((obs, oldVal, newVal)
                -> obtenerCosto());
    }

    private void obtenerCosto() {
        costoAdicional = costoAdicionalTxt.getText();
    }
}
