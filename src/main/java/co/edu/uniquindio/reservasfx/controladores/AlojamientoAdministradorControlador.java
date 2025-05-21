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
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlojamientoAdministradorControlador {
    @FXML
    private TableView<ServicioSeleccionable> tablaServiciosIncluidos;
    @FXML
    private ImageView imagenPrincipal;
    @FXML
    private TableColumn<ServicioSeleccionable, String> serviviosIncluidosColumn;
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
    private TableColumn<ServicioSeleccionable, Boolean> incluirColumna;

    private String rutaImagenPrincipal;
    private String rutaImagenSecundaria1;
    private String rutaImagenSecundaria2;
    private String rutaImagenSecundaria3;


    private final ObservableList<ServicioSeleccionable> listaServicios = FXCollections.observableArrayList();
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
        tablaServiciosIncluidos.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case CONTROL -> {
                    guardarServicios();
                }
            }
        });

    }

    public void initTabla() {
        try {
            ArrayList<Servicio> serviciosIncluidos = controlador.getEmpresa()
                    .getModuloAlojamientoServicios()
                    .obtenerServiciosAlojamiento(alojamiento.getId());

            ArrayList<ServicioSeleccionable> lista = new ArrayList<>();

            for (TipoServicio tipo : TipoServicio.values()) {
                boolean yaIncluido = serviciosIncluidos.stream()
                        .anyMatch(s -> s.getTipo().equals(tipo));

                Servicio servicio = new Servicio(alojamiento.getId(), tipo);
                lista.add(new ServicioSeleccionable(servicio, yaIncluido));
            }

            incluirColumna.setCellValueFactory(cellData -> cellData.getValue().seleccionadoProperty());
            incluirColumna.setCellFactory(CheckBoxTableCell.forTableColumn(incluirColumna));

            serviviosIncluidosColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getServicio().getNombre()));

            tablaServiciosIncluidos.setItems(FXCollections.observableArrayList(lista));

        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public ArrayList<Servicio> guardarServicios() {
        List<Servicio> seleccionados = tablaServiciosIncluidos.getItems().stream()
                .filter(ServicioSeleccionable::isSeleccionado)
                .map(ServicioSeleccionable::getServicio)
                .collect(Collectors.toList());
        ArrayList<Servicio> servicios = new ArrayList<>(seleccionados);

        return servicios;
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

            ArrayList<Servicio> serviciosAlojamiento = guardarServicios();
            ArrayList<TipoServicio> tipoServicios = new ArrayList<>();
            for (Servicio servicio : serviciosAlojamiento) {
                TipoServicio tipoServicio = servicio.getTipo();
                tipoServicios.add(tipoServicio);
            }

            controlador.getEmpresa().getModuloAlojamientoServicios().editarAlojamiento(alojamiento.getId(),
                    tipoAlojamiento, nombre, descripcion, precioNoche, capacidad, tipoServicios,
                    urlPrincipal, imagenes, costoAdicional.costoAdicional, habitaciones);
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

    private void cargarInformacionAlojamiento() {
        if (alojamiento != null) {
            NombreText.setText(alojamiento.getNombre());
            txtNombre.setText(alojamiento.getNombre());
            txtDescripcion.setText(alojamiento.getDescripcion());
            txtCapacidad.setText(Integer.toString(alojamiento.getCapacidadMaxima()));
            txtPrecioNoche.setText(Double.toString(alojamiento.getPrecioPorNoche()));
            controlador.cargarImagen(alojamiento.getImagenPrincipal(), imagenPrincipal);
        }
    }

    private void cargarTabsDinamicamente() {
        if (alojamiento != null) {
            String tipo = alojamiento.getClass().getSimpleName();

            try {
                String rutaInfo;
                if (tipo.equals("Hotel")) {
                    rutaInfo = "/co/edu/uniquindio/reservasfx/listaHabitacionesAlojamiento.fxml";
                } else {
                    rutaInfo = "/co/edu/uniquindio/reservasfx/costoAdicionalAlojamiento.fxml";
                }
                controlador.cargarEnTab(adicionalTab, rutaInfo);

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
