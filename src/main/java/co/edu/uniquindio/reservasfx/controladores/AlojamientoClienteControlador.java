package co.edu.uniquindio.reservasfx.controladores;
import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.SeleccionReserva;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.ImagenRepositorio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;

public class AlojamientoClienteControlador {

    @FXML
    private Tab reseniaHuespedTab;

    @FXML
    private Button volverBtn;

    @FXML
    private HBox thumbnailContainer;

    @FXML
    private ImageView imagen2;

    @FXML
    private Button seguirBtn;

    @FXML
    private Text nombreText;

    @FXML
    private ImageView imagen1;

    @FXML
    private StackPane contentPane;

    @FXML
    private Button reservarBtn;

    @FXML
    private Text capacidadText;

    @FXML
    private Text descripcionText;

    @FXML
    private Rectangle imagen3;

    @FXML
    private Tab ofertasEspecialesTab;

    @FXML
    private Tab adicionalTab;

    @FXML
    private Button anteriorBtn;

    @FXML
    private Text precioText;

    @FXML
    private Text ciudadText;

    @FXML
    private ScrollPane offersScrollPane;

    @FXML
    private ImageView imagenPrincipal;

    @FXML
    private Text huespedesText;

    @FXML
    private DatePicker fechaInicioDate;

    @FXML
    private Text fechaFinText;

    @FXML
    private DatePicker fechaFinDate;

    @FXML
    private Text fechaInicioText;


    @FXML
    private TextField txtHuespedes;


    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();
    String rutaAnterior;
    Sesion sesion = controlador.getSesion();
    Alojamiento alojamiento = AlojamientoSelect.getInstancia().getAlojamiento();
    private ArrayList<String> rutasImagenes;
    private int imagenActual = 0;
    ImagenRepositorio imagenRepositorio;
    private ArrayList<Imagen> listaImagenes;  // Lista con objetos Imagen
    private int indiceImagenActual = 0;


    @FXML
    void anteriorBtnAction(ActionEvent event) {
        if (rutasImagenes == null || rutasImagenes.isEmpty()) return;

        imagenActual = (imagenActual - 1 + rutasImagenes.size()) % rutasImagenes.size();
        mostrarImagenActual();
    }

    @FXML
    void seguirBtnAction(ActionEvent event) {
        if (rutasImagenes == null || rutasImagenes.isEmpty()) return;

        imagenActual = (imagenActual + 1) % rutasImagenes.size();
        mostrarImagenActual();
    }


    @FXML
    void reservarBtnAction(ActionEvent event) {
        try {
            String cedula = sesion.getUsuario().getCedula();
            String idAlojamiento = AlojamientoSelect.getInstancia().getAlojamiento().getId();
            LocalDate inicio = fechaInicioDate.getValue();
            LocalDate fin    = fechaFinDate.getValue();
            int numHues = Integer.parseInt(huespedesText.getText());

            Habitacion habitacion = SeleccionReserva.getInstancia().getHabitacionSeleccionada();
            int numeroHabitacion = (habitacion != null) ? habitacion.getNumero() : -1;

            controlador.getEmpresa().getModuloComercialServicios().realizarReserva(cedula, idAlojamiento, inicio, fin, numHues, numeroHabitacion
                    );
            controlador.crearAlerta("Reserva realizada exitosamente", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            controlador.crearAlerta("Número de huéspedes inválido", Alert.AlertType.WARNING);
        } catch (Exception e) {
            controlador.crearAlerta("Error al reservar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void volverBtnAction(ActionEvent event) {
        panePrincipalControlador.actualizarInferior(rutaAnterior);
    }

    void inicializarValores(String ruta) {
        this.rutaAnterior = ruta;
        cargarImagenesDelAlojamiento();
    }


    @FXML
    void initialize() {
        cargarInformacionAlojamiento();
        cargarTabsDinamicamente();
        cargarImagenesDelAlojamiento();

    }

    private void cargarInformacionAlojamiento() {
        if (alojamiento != null) {
            nombreText.setText(nombreText.getText() + ":" + alojamiento.getNombre());
            ciudadText.setText(ciudadText.getText() + ": " + alojamiento.getCiudad().getNombre());
            descripcionText.setText(descripcionText.getText() + ":" + alojamiento.getDescripcion());
            capacidadText.setText(capacidadText.getText() + ":" + alojamiento.getCapacidadMaxima());
            precioText.setText(precioText.getText() + ":" + alojamiento.getPrecioPorNoche());
        }
    }

    private void cargarTabsDinamicamente() {
        if (alojamiento != null) {
            String tipo = alojamiento.getClass().getSimpleName(); // "Casa", "Apartamento", "Hotel"

            try {
                String rutaInfo;
                if (tipo.equals("Casa") || tipo.equals("Apartamento")) {
                    rutaInfo = "/co/edu/uniquindio/reservasfx/costoAdicional.fxml";
                } else if (tipo.equals("Hotel")) {
                    rutaInfo = "/co/edu/uniquindio/reservasfx/listaHabitaciones.fxml";
                } else {
                    rutaInfo = null;
                }
                if (rutaInfo != null) {
                    controlador.cargarEnTab(adicionalTab, rutaInfo);
                }

                controlador.cargarEnTab(reseniaHuespedTab, "/co/edu/uniquindio/reservasfx/listaResenias.fxml");

                controlador.cargarEnTab(ofertasEspecialesTab, "/co/edu/uniquindio/reservasfx/listaOfertas.fxml");

            } catch (Exception e) {
                controlador.crearAlerta("Error cargando tabs: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    void cargarImagenesDelAlojamiento() {
        try {
            String idAlojamiento = alojamiento.getId();
            ArrayList<Imagen> listaImagenes = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerImagenesAlojamiento(idAlojamiento);

            ArrayList<String> rutasImagenes = new ArrayList<>();

            for (Imagen img : listaImagenes) {
                rutasImagenes.add(img.getRuta());
            }

            cargarThumbnails(rutasImagenes);
        }catch (Exception e){
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    void cargarThumbnails(ArrayList<String> rutasImagenes) {
        thumbnailContainer.getChildren().clear();

        for (String ruta : rutasImagenes) {
            ImageView imgView = new ImageView(new Image(ruta));
            imgView.setFitWidth(100);
            imgView.setFitHeight(100);
            thumbnailContainer.getChildren().add(imgView);
        }
    }

    private void mostrarImagenActual() {
        if (listaImagenes != null && !listaImagenes.isEmpty() && indiceImagenActual < listaImagenes.size()) {
            Imagen imagen = listaImagenes.get(indiceImagenActual);
            imagenPrincipal.setImage(new Image(imagen.getRuta()));
        }
    }


}
