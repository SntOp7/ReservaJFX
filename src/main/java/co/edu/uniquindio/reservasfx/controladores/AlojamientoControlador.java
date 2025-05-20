package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Administrador;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.modelo.factory.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

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
    private Button consultarBtn;
    @FXML
    private ImageView imagenCorazon;
    @FXML
    private ImageView imagenAlojamiento;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();

    Alojamiento alojamiento = null;

    public void inicializarValores(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
        nombreLbl.setText(alojamiento.getNombre());
        precioLbl.setText(alojamiento.getPrecioPorNoche() + "");
        capacidadLbl.setText(alojamiento.getCapacidadMaxima() + "");
        ubicacionLbl.setText(alojamiento.getCiudad().getNombre());
        controlador.cargarImagen(alojamiento.getImagenPrincipal(), imagenAlojamiento);
        if (controlador.getSesion().getUsuario() != null) {
            try {
                ArrayList<Deseo> deseosCliente = controlador.getEmpresa().getModuloUsuarioServicios()
                        .obtenerDeseosCliente(controlador.getSesion().getUsuario().getCedula());
                ArrayList<Alojamiento> alojamientosDeseos = controlador.getEmpresa().getModuloAlojamientoServicios()
                        .obtenerAlojamientosPorDeseosCliente(deseosCliente);
                if (alojamientosDeseos != null && alojamientosDeseos.contains(alojamiento)) {
                    Image imagen = ControladorPrincipal.cargarImagenSeleccionada("corazonRelleno.png");
                    imagenCorazon.setImage(imagen);
                }
            } catch (Exception e) {
                controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void favoritoBtnAction(ActionEvent event) {
        if (controlador.getSesion().getUsuario() != null) {
            if (controlador.getSesion().getUsuario() instanceof Administrador) {
                controlador.crearAlerta("Solo los clientes pueden agregar a la lista de deseos", Alert.AlertType.ERROR);
            } else {
                String cedula = controlador.getSesion().getUsuario().getCedula();
                try {
                    ArrayList<Deseo> deseosCliente = controlador.getEmpresa().getModuloUsuarioServicios()
                            .obtenerDeseosCliente(controlador.getSesion().getUsuario().getCedula());
                    ArrayList<Alojamiento> alojamientosDeseos = controlador.getEmpresa().getModuloAlojamientoServicios()
                            .obtenerAlojamientosPorDeseosCliente(deseosCliente);
                    if (alojamientosDeseos != null && alojamientosDeseos.contains(alojamiento)) {
                        controlador.getEmpresa().getModuloUsuarioServicios().eliminarDeseo(cedula, alojamiento.getId());
                        Image imagen = ControladorPrincipal.cargarImagenSeleccionada("corazonSinRelleno.png");
                        imagenCorazon.setImage(imagen);
                        controlador.crearAlerta("Se ha eliminado el alojamiento de tu lista de deseos",
                                Alert.AlertType.INFORMATION);
                    } else {
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
                        Image imagen = ControladorPrincipal.cargarImagenSeleccionada("corazonRelleno.png");
                        imagenCorazon.setImage(imagen);
                    }
                } catch (Exception e) {
                    controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        } else {
            controlador.crearAlerta("Debe estar logueado para poder agregar a la lista de deseos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void consultarBtnAction(ActionEvent event) {
        if (controlador.getSesion().getUsuario() != null) {
            controlador.getAlojamientoSelect().setAlojamiento(alojamiento);
            panePrincipalControlador.limpiarInferior();
            panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/alojamientoCliente.fxml");
        } else {
            controlador.crearAlerta("Debe estar logueado para consultar el alojamiento", Alert.AlertType.ERROR);
        }
    }
}
