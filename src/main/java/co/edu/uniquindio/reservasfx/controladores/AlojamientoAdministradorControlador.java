package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.SeleccionReserva;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.ServicioSeleccionable;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AlojamientoAdministradorControlador {
    @FXML
    private TableView<TipoServicio> tablaServiciosIncluidos;
    @FXML
    private ImageView imagenPrincipal;
    @FXML
    private TableColumn<TipoServicio, String> serviviosIncluidosColumn;
    @FXML
    private Tab reseniaHuespedTab;
    @FXML
    private Button editarBtn;
    @FXML
    private HBox thumbnailContainer;
    @FXML
    private Text nombreText;
    @FXML
    private Text NombreText;
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
    private TextField txtNombre;
    @FXML
    private TextField txtCapacidad;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecioNoche;
    @FXML
    private Tab estadisticasTab;

    private String rutaImagenPrincipal;
    private String rutaImagenSecundaria1;
    private String rutaImagenSecundaria2;
    private String rutaImagenSecundaria3;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    PanePrincipalControlador panePrincipalControlador = PanePrincipalControlador.getInstancia();
    CostoAdicionalAlojamientoControlador costoAdicionalAlojamientoControlador;
    ListaHabitacionesAlojamientoControlador listaHabitacionesAlojamientoControlador;

    private final ObservableList<TipoServicio> listaServicios = FXCollections.observableArrayList();
    ObservableList<TipoServicio> tipoServiciosAlojamiento = FXCollections.observableArrayList();

    Alojamiento alojamiento = AlojamientoSelect.getInstancia().getAlojamiento();

    ImageView[] imagenes = {imagen1, imagen2, imagen3};

    @FXML
    void initialize() {
        imagenes = new ImageView[] {imagen1, imagen2, imagen3};
        cargarInformacionAlojamiento();
        cargarTabsDinamicamente();
        initTabla();
        initData();
        tablaServiciosIncluidos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            ArrayList<Servicio> serviciosAlojamiento = controlador.getEmpresa()
                    .getModuloAlojamientoServicios().obtenerServiciosAlojamiento(alojamiento.getId());
            for (Servicio servicio : serviciosAlojamiento) {
                TipoServicio tipoServicio = servicio.getTipo();
                tipoServiciosAlojamiento.add(tipoServicio);
            }
            for (TipoServicio tipoServicio : tipoServiciosAlojamiento) {
                tablaServiciosIncluidos.getSelectionModel().select(tipoServicio);
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void initData() {
        TipoServicio[] tipoServicios = TipoServicio.values();
        Collections.addAll(listaServicios, tipoServicios);
        tablaServiciosIncluidos.setItems(listaServicios);
    }

    public void initTabla() {
        serviviosIncluidosColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
    }

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
        try {
            String imagenPrincipal = alojamiento.getImagenPrincipal();
            String urlPrincipal = rutaImagenPrincipal == null ? imagenPrincipal : rutaImagenPrincipal;
            ArrayList<Imagen> imagenesAlojamiento = controlador.getEmpresa()
                    .getModuloAlojamientoServicios().obtenerImagenesAlojamiento(alojamiento.getId());

            ArrayList<String> rutaImagenesAlojamiento = new ArrayList<>();
            for (Imagen imagen : imagenesAlojamiento) {
                rutaImagenesAlojamiento.add(imagen.getRuta());
            }

            String urlSecundaria1 = rutaImagenSecundaria1 == null && rutaImagenesAlojamiento.size() > 0
                    ? rutaImagenesAlojamiento.get(0) : rutaImagenSecundaria1;
            String urlSecundaria2 = rutaImagenSecundaria2 == null && rutaImagenesAlojamiento.size() > 1
                    ? rutaImagenesAlojamiento.get(1) : rutaImagenSecundaria2;
            String urlSecundaria3 = rutaImagenSecundaria3 == null && rutaImagenesAlojamiento.size() > 2
                    ? rutaImagenesAlojamiento.get(2) : rutaImagenSecundaria3;

            ArrayList<String> imagenes = new ArrayList<>();
            if (urlSecundaria1 != null) imagenes.add(urlSecundaria1);
            if (urlSecundaria2 != null) imagenes.add(urlSecundaria2);
            if (urlSecundaria3 != null) imagenes.add(urlSecundaria3);
            TipoAlojamiento tipoAlojamiento = null;
            if (alojamiento instanceof Hotel) {
                tipoAlojamiento = TipoAlojamiento.HOTEL;
            }
            if (alojamiento instanceof Casa) {
                tipoAlojamiento = TipoAlojamiento.CASA;
            }
            if (alojamiento instanceof Apartamento) {
                tipoAlojamiento = TipoAlojamiento.APARTAMENTO;
            }
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            String capacidad = txtCapacidad.getText();
            String precioNoche = txtPrecioNoche.getText();
            ObservableList<TipoServicio> serviciosSeleccionados = tablaServiciosIncluidos.getSelectionModel()
                    .getSelectedItems();
            ArrayList<TipoServicio> servicios = new ArrayList<>(serviciosSeleccionados);
            System.out.println("Secundaria 1: " + urlSecundaria1);
            System.out.println("Secundaria 2: " + urlSecundaria2);
            System.out.println("Secundaria 3: " + urlSecundaria3);
            if (alojamiento instanceof Hotel) {
                controlador.getEmpresa().getModuloAlojamientoServicios().editarAlojamiento(alojamiento.getId(),
                        tipoAlojamiento, nombre, descripcion, precioNoche, capacidad, servicios,
                        urlPrincipal, imagenes, null, listaHabitacionesAlojamientoControlador
                                .getHabitaciones());
            } else {
                controlador.getEmpresa().getModuloAlojamientoServicios().editarAlojamiento(alojamiento.getId(),
                        tipoAlojamiento, nombre, descripcion, precioNoche, capacidad, servicios,
                        urlPrincipal, imagenes, costoAdicionalAlojamientoControlador.getCostoAdicional(), null);
            }
            controlador.crearAlerta("Alojamiento editado con exito",Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            controlador.crearAlerta("Error al editar el alojamiento: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void eliminarBtnAction(ActionEvent event) {
        if (alojamiento == null) {
            controlador.crearAlerta("No hay alojamiento seleccionado", Alert.AlertType.ERROR);
            return;
        }

        try {
            controlador.getEmpresa().getModuloAlojamientoServicios().eliminarAlojamiento(alojamiento.getId());
            controlador.crearAlerta("Alojamiento eliminado con éxito", Alert.AlertType.INFORMATION);
            panePrincipalControlador.actualizarInferior("/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
        } catch(Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarInformacionAlojamiento() {
        if (alojamiento != null) {
            NombreText.setText(alojamiento.getNombre());
            txtNombre.setText(alojamiento.getNombre());
            txtDescripcion.setText(alojamiento.getDescripcion());
            txtCapacidad.setText(Integer.toString(alojamiento.getCapacidadMaxima()));
            txtPrecioNoche.setText(Double.toString(alojamiento.getPrecioPorNoche()));
            System.out.println(alojamiento.getImagenPrincipal());
            controlador.cargarImagen(alojamiento.getImagenPrincipal(), imagenPrincipal);
            cargarImagenesDelAlojamiento();
        }
    }

    private void cargarTabsDinamicamente() {
        if (alojamiento != null) {
            try {
                String rutaInfo;
                if (alojamiento instanceof Hotel) {
                    rutaInfo = "/co/edu/uniquindio/reservasfx/listaHabitacionesAlojamiento.fxml";
                    cargarEnTabHotel(adicionalTab, rutaInfo);
                } else {
                    rutaInfo = "/co/edu/uniquindio/reservasfx/costoAdicionalAlojamiento.fxml";
                    cargarEnTab(adicionalTab, rutaInfo);
                }

                controlador.cargarEnTab(reseniaHuespedTab, "/co/edu/uniquindio/reservasfx/listaResenias.fxml");

                controlador.cargarEnTab(ofertasEspecialesTab, "/co/edu/uniquindio/reservasfx/listaOfertasAlojamiento.fxml");

                controlador.cargarEnTab(estadisticasTab, "/co/edu/uniquindio/reservasfx/estadisticasAlojamiento.fxml");
            } catch (Exception e) {
                controlador.crearAlerta("Error cargando tabs: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    void cargarImagenesDelAlojamiento() {
        try {
            String idAlojamiento = alojamiento.getId();
            ArrayList<Imagen> listaImagenes = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerImagenesAlojamiento(idAlojamiento);
            if (listaImagenes == null || listaImagenes.isEmpty() ) {
                return;
            }
            ArrayList<String> rutaImagenes = new ArrayList<>();
            for (Imagen imagen : listaImagenes) {
                rutaImagenes.add(imagen.getRuta());
            }
            for (int i = 0; i < imagenes.length; i++) {
                if (i < rutaImagenes.size()) {
                    controlador.cargarImagen(rutaImagenes.get(i), imagenes[i]);
                }
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void cargarEnTab(Tab tab, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Node contenido = loader.load();
            costoAdicionalAlojamientoControlador = loader.getController();
            tab.setContent(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarEnTabHotel(Tab tab, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Node contenido = loader.load();
            listaHabitacionesAlojamientoControlador = loader.getController();
            tab.setContent(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
