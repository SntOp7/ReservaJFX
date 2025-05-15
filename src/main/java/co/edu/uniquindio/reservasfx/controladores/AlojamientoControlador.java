package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class AlojamientoControlador {
    @FXML
    private Label capacidadLbl;
    @FXML
    private Label precioLbl;
    @FXML
    private Label nombreLbl;
    @FXML
    private Label ubicacionLbl;
    @FXML
    private ImageView imagenAlojamiento;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    public void inicializarValores(Alojamiento alojamiento) {
        nombreLbl.setText(alojamiento.getNombre());
        precioLbl.setText(alojamiento.getPrecioPorNoche() + "");
        capacidadLbl.setText(alojamiento.getCapacidadMaxima() + "");
        ubicacionLbl.setText(alojamiento.getCiudad().getNombre());
        controlador.cargarImagen(alojamiento.getImagenPrincipal(), imagenAlojamiento);
    }
}
