package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.modelo.factory.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class AlojamientoOfertadoControlador {
    @FXML
    private Label capacidadLbl;
    @FXML
    private Button favoritoOfertaBtn;
    @FXML
    private Label precioLbl;
    @FXML
    private Label nombreLbl;
    @FXML
    private Button consultarOfertaBtn;
    @FXML
    private Label ubicacionLbl;
    @FXML
    private ImageView imagenAlojamiento;
    @FXML
    private Label ofertalbl;
    @FXML
    private ImageView imagenCorazon;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    Alojamiento alojamiento = null;

    public void inicializarValores(Alojamiento alojamiento) {
        try {
            this.alojamiento = alojamiento;
            nombreLbl.setText(alojamiento.getNombre());
            precioLbl.setText(alojamiento.getPrecioPorNoche() + "");
            capacidadLbl.setText(alojamiento.getCapacidadMaxima() + "");
            ubicacionLbl.setText(alojamiento.getCiudad().getNombre());
            controlador.cargarImagen(alojamiento.getImagenPrincipal(), imagenAlojamiento);
            ArrayList<Oferta> ofertas = controlador.getEmpresa().getModuloComercialServicios().obtenerOfertasActivasAlojamiento(alojamiento.getId());
            Oferta ofertaActual = ofertas.getFirst();
            String tipoOferta = ofertaActual.getOfertaEspecial().getNombre();
            ofertalbl.setText((tipoOferta));
        } catch (Exception e){
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void favoritoBtnAction(ActionEvent event) {
        String cedula = controlador.getSesion().getUsuario().getCedula();
        if (alojamiento != null) {
            try {
                controlador.getEmpresa().getModuloUsuarioServicios().guardarDeseo(cedula, alojamiento.getId());
                String mensaje = "";
                if (alojamiento instanceof Casa) {
                    mensaje += "Casas";
                } else if (alojamiento instanceof Apartamento) {
                    mensaje += "Apartamentos";
                } else if (alojamiento instanceof Hotel) {
                    mensaje += "Hoteles";
                }
                controlador.crearAlerta("Se ha agregado el alojamiento a tu lista de deseos de " + mensaje,
                        Alert.AlertType.INFORMATION);
                controlador.cargarImagen("@img\\corazonRelleno.png", imagenCorazon);
            } catch (Exception e) {
                controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void consultarBtnAction(ActionEvent event) {
        controlador.getAlojamientoSelect().setAlojamiento(alojamiento);
        panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/alojamientoCliente.fxml");
    }
}
