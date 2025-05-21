package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.SeleccionReserva;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.modelo.factory.Hotel;
import co.edu.uniquindio.reservasfx.repositorios.ImagenRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;

public class AlojamientoAdministradorControlador {
    @FXML
    private TableView<Servicio> tablaServiciosIncluidos;
    @FXML
    private ImageView imagenPrincipal;
    @FXML
    private TableColumn<Servicio, String> serviviosIncluidosColumn;
    @FXML
    private Tab reseniaHuespedTab;
    @FXML
    private Button editarBtn;
    @FXML
    private HBox thumbnailContainer;
    @FXML
    private Text nombreText;
    @FXML
    private ImageView imagen1;
    @FXML
    private Button subirImagenBtn;
    @FXML
    private Button subirNuevasImagenesBtn;
    @FXML
    private Button eliminarBtn;
    @FXML
    private ImageView imagen2;
    @FXML
    private ImageView imagen3;
    @FXML
    private Text capacidadText;
    @FXML
    private Text descripcionText;
    @FXML
    private Tab ofertasEspecialesTab;
    @FXML
    private Tab adicionalTab;
    @FXML
    private Text precioText;
    @FXML
    private Text ciudadText;

    private String rutaImagenPrincipal;
    private String rutaImagenSecundaria1;
    private String rutaImagenSecundaria2;
    private String rutaImagenSecundaria3;

    @FXML
    void subirImagenBtnAction(ActionEvent event) {
        rutaImagenPrincipal = controlador.seleccionarImagen(imagenPrincipal);
    }

    @FXML
    void subirNuevasImagenesBtnAction(ActionEvent event) {
        rutaImagenSecundaria1 = controlador.seleccionarImagen(imagen1);
        rutaImagenSecundaria2 = controlador.seleccionarImagen((imagen2));
        rutaImagenSecundaria3 = controlador.seleccionarImagen((imagen3));
    }

    @FXML
    void editarBtnAction(ActionEvent event) {
        String urlPrincipal = rutaImagenPrincipal == null ? null : rutaImagenPrincipal;
        String urlSecundaria1 = rutaImagenSecundaria1 == null ? null : rutaImagenSecundaria1;
        String urlSecundaria2 = rutaImagenSecundaria2 == null ? null : rutaImagenSecundaria2;
        String urlSecundaria3 = rutaImagenSecundaria3 == null ? null : rutaImagenSecundaria3;
        ArrayList<String> imagenes = new ArrayList<>();
        imagenes.add(urlSecundaria1);
        imagenes.add(urlSecundaria2);
        imagenes.add(urlSecundaria3);
        TipoAlojamiento tipoAlojamiento = null;
        ArrayList<Habitacion> habitaciones = listaHabitacionesAlojamientoControlador.habitaciones;
        try {
            if (alojamiento instanceof Hotel) {
                tipoAlojamiento = TipoAlojamiento.HOTEL;

            }
            if (alojamiento instanceof Casa) {
                tipoAlojamiento = TipoAlojamiento.CASA;
            }
            if (alojamiento instanceof Apartamento) {
                tipoAlojamiento = TipoAlojamiento.APARTAMENTO;
            }
            String nombre = nombreText.getText();
            String descripcion = descripcionText.getText();
            String capacidad = capacidadText.getText();
            String precioNoche = precioText.getText();

            ArrayList<Servicio> serviciosAlojamiento = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerServiciosAlojamiento(alojamiento.getId());
            ArrayList<TipoServicio> tipoServicios = new ArrayList<>();
            for (Servicio servicio : serviciosAlojamiento) {
                TipoServicio tipoServicio = servicio.getTipo();
                tipoServicios.add(tipoServicio);
            }

            controlador.getEmpresa().getModuloAlojamientoServicios().editarAlojamiento(alojamiento.getId(),
                    tipoAlojamiento, nombre, descripcion, precioNoche, capacidad, tipoServicios,
                    imagenPrincipal.getImage().getUrl(), imagenes, costoAdicional.costoAdicional, habitaciones);
            controlador.crearAlerta("Alojamiento editado con exito",Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void eliminarBtnAction(ActionEvent event) {
        try{
            controlador.getEmpresa().getModuloAlojamientoServicios().eliminarAlojamiento(alojamiento.getId());
            controlador.crearAlerta("Alojamiento eliminado con exito",Alert.AlertType.INFORMATION);
        }catch(Exception e){
            controlador.crearAlerta(e.getMessage(),Alert.AlertType.ERROR);
        }
    }


    private final ObservableList<Servicio> listaServicios = FXCollections.observableArrayList();
    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();
    ListaHabitacionesAlojamientoControlador listaHabitacionesAlojamientoControlador =new ListaHabitacionesAlojamientoControlador();
    CostoAdicionalAlojamientoControlador costoAdicional = new CostoAdicionalAlojamientoControlador();
    String rutaAnterior;
    Sesion sesion = controlador.getSesion();
    Alojamiento alojamiento = AlojamientoSelect.getInstancia().getAlojamiento();
    private ArrayList<String> rutasImagenes;
    private int imagenActual = 0;
    ImagenRepositorio imagenRepositorio;
    private ArrayList<Imagen> listaImagenes;
    private int indiceImagenActual = 0;

    void inicializarValores(String ruta) {
        this.rutaAnterior = ruta;
        cargarImagenesDelAlojamiento();
    }


    @FXML
    void initialize() {
        cargarInformacionAlojamiento();
        cargarTabsDinamicamente();
        cargarImagenesDelAlojamiento();
        initTabla();

    }

    public void initTabla() {
        try {
            serviviosIncluidosColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getTipo().getNombre()));


            ArrayList<Servicio> servicios = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerServiciosAlojamiento(alojamiento.getId());
            listaServicios.setAll(servicios);
            tablaServiciosIncluidos.setItems(listaServicios);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void cargarInformacionAlojamiento() {
        if (alojamiento != null) {
            nombreText.setText(alojamiento.getNombre());
            ciudadText.setText(ciudadText.getText() + " : " + alojamiento.getCiudad().getNombre());
            descripcionText.setText(descripcionText.getText() + " : " + alojamiento.getDescripcion());
            capacidadText.setText(capacidadText.getText() + " : " + alojamiento.getCapacidadMaxima());
            precioText.setText(precioText.getText() + " : " + alojamiento.getPrecioPorNoche());
            controlador.cargarImagen(alojamiento.getImagenPrincipal(), imagenPrincipal);
        }
    }

    private void cargarTabsDinamicamente() {
        if (alojamiento != null) {
            String tipo = alojamiento.getClass().getSimpleName();

            try {
                String rutaInfo;
                if (tipo.equals("Casa") || tipo.equals("Apartamento")) {
                    rutaInfo = "/co/edu/uniquindio/reservasfx/costoAdicionalAlojamineto.fxml";
                } else if (tipo.equals("Hotel")) {
                    rutaInfo = "/co/edu/uniquindio/reservasfx/listaHabitacionesAlojamiento.fxml";
                } else {
                    rutaInfo = null;
                }
                if (rutaInfo != null) {
                    controlador.cargarEnTab(adicionalTab, rutaInfo);
                }

                controlador.cargarEnTab(reseniaHuespedTab, "/co/edu/uniquindio/reservasfx/listaResenias.fxml");

                controlador.cargarEnTab(ofertasEspecialesTab, "/co/edu/uniquindio/reservasfx/listaOfertasAlojamiento.fxml");

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
