package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Getter;

public class CostoAdicionalAlojamientoControlador {
    @FXML
    private TextField costoAdicionalTxt;

    @Getter
    String costoAdicional;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    @FXML
    void initialize() {
        Alojamiento alojamiento = controlador.getAlojamientoSelect().getAlojamiento();
        if (alojamiento != null) {
            if (alojamiento instanceof Casa) {
                costoAdicionalTxt.setText(((Casa) alojamiento).getCostoAseoYMantenimiento() + "");
                costoAdicional = costoAdicionalTxt.getText();
            } else {
                costoAdicionalTxt.setText(((Apartamento) alojamiento).getCostoAseoYMantenimiento() + "");
                costoAdicional = costoAdicionalTxt.getText();
            }
        }
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
