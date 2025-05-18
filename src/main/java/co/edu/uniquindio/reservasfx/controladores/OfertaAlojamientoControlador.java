package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class OfertaAlojamientoControlador {

    @FXML
    private Label capacidadLbl;

    @FXML
    private Label precioLbl;

    @FXML
    private Label nombreLbl;

    @FXML
    private Label ubicacionLbl;

    @FXML
    private ImageView imagenAlojamientoOferta;

    @FXML
    private Label ofertaLbl;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    public void inicializarValores(Alojamiento alojamiento) {
        try {
            nombreLbl.setText(alojamiento.getNombre());
            precioLbl.setText(alojamiento.getPrecioPorNoche() + "");
            capacidadLbl.setText(alojamiento.getCapacidadMaxima() + "");
            ubicacionLbl.setText(alojamiento.getCiudad().getNombre());
            controlador.cargarImagen(alojamiento.getImagenPrincipal(), imagenAlojamientoOferta);
            ArrayList<Oferta> ofertas = controlador.getEmpresa().getModuloComercialServicios().obtenerOfertasAlojamiento(alojamiento.getId());
            Oferta ofertaActual = ofertas.getFirst();
            String tipoOferta = ofertaActual.getOfertaEspecial().getNombre();
            ofertaLbl.setText((tipoOferta));
        }catch (Exception e){
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }




}
