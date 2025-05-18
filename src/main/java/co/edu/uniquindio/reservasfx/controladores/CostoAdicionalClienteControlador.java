package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CostoAdicionalClienteControlador {

    @FXML
    private Label costoAseoMantenimientoLabel;

    @FXML
    private Label tipoAlojamientoLabel;

    Alojamiento alojamiento = AlojamientoSelect.getInstancia().getAlojamiento();


    public void inicializar(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
        cargarDatos();
    }

    private void cargarDatos() {
        if (alojamiento != null) {
            tipoAlojamientoLabel.setText(alojamiento.getTipo());

            if (alojamiento instanceof Casa) {
                Casa casa = (Casa) alojamiento;
                costoAseoMantenimientoLabel.setText(costoAseoMantenimientoLabel.getText() + ":" + casa.getCostoAseoYMantenimiento());
            } else if (alojamiento instanceof Apartamento) {
                Apartamento apto = (Apartamento) alojamiento;
                costoAseoMantenimientoLabel.setText(costoAseoMantenimientoLabel.getText() + ":" +  apto.getCostoAseoYMantenimiento());
            } else {
                costoAseoMantenimientoLabel.setText("No aplica");
            }
        }
    }
}
