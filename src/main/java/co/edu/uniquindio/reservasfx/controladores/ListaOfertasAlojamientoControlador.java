package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoOferta;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListaOfertasAlojamientoControlador {
    @FXML
    private Button editarBtn;
    @FXML
    private ComboBox<String> tipoOfertaBox;
    @FXML
    private TextField descripcionField;
    @FXML
    private DatePicker inicioDate;
    @FXML
    private Button agregarBtn;
    @FXML
    private Button eliminarBtn;
    @FXML
    private TextField descuentoField;
    @FXML
    private TextField nombreField;
    @FXML
    private DatePicker finDate;
    @FXML
    private TableView<Oferta> tablaOfertas;
    @FXML
    private TableColumn<Oferta, String> clmNombre;
    @FXML
    private TableColumn<Oferta, String> clmTipoOferta;
    @FXML
    private TableColumn<Oferta, String> clmEstadoOferta;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    ObservableList<Oferta> ofertasTabla;
    ArrayList<Oferta> ofertas = new ArrayList<>();

    private Oferta ofertaSelected;

    @FXML
    void initialize() {
        ofertasTabla = FXCollections.observableArrayList();
        OfertaEspecial[] ofertasEspeciales = OfertaEspecial.values();
        for (OfertaEspecial ofertaEspecial : ofertasEspeciales) {
            tipoOfertaBox.getItems().add(ofertaEspecial.getNombre());
        }
        initTabla();
        if (controlador.getAlojamientoSelect().getAlojamiento() != null) {
            initData();
        }
        seleccionarOferta();
    }

    private void seleccionarOferta() {
        tablaOfertas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            ofertaSelected = newSelection;
            cargarDatos();
        });
    }

    private void cargarDatos() {
        if (ofertaSelected != null) {
            nombreField.setText(ofertaSelected.getNombre());
            tipoOfertaBox.getSelectionModel().select(ofertaSelected.getOfertaEspecial().getNombre());
            tipoOfertaBox.setDisable(true);
            descripcionField.setText(ofertaSelected.getDescripcion());
            inicioDate.setValue(ofertaSelected.getFechaInicio());
            finDate.setValue(ofertaSelected.getFechaFin());
            descuentoField.setText(ofertaSelected.getPorcentajeDescuento() + "");
        }
    }

    private void initTabla() {
        clmNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        clmEstadoOferta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado().getNombre()));
        clmTipoOferta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getOfertaEspecial().getNombre()));
    }

    private void initData() {
        try {
            ofertas = controlador.getEmpresa().getModuloComercialServicios()
                    .obtenerOfertasAlojamiento(controlador.getAlojamientoSelect().getAlojamiento().getId());
            ofertasTabla.setAll(ofertas);
            tablaOfertas.setItems(ofertasTabla);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void agregarBtnAction(ActionEvent event) {
        String nombre = nombreField.getText();
        String tipoOferta = tipoOfertaBox.getSelectionModel().getSelectedItem();
        String descripcion = descripcionField.getText();
        LocalDate inicio = inicioDate.getValue();
        LocalDate fin = finDate.getValue();
        String porcentajeDescuento = descuentoField.getText();
        try {
            controlador.getEmpresa().getModuloComercialServicios().registrarOferta(tipoOferta,
                    controlador.getAlojamientoSelect().getAlojamiento().getId(), nombre, descripcion, inicio, fin, porcentajeDescuento);
            ofertas = controlador.getEmpresa().getModuloComercialServicios()
                    .obtenerOfertasAlojamiento(controlador.getAlojamientoSelect().getAlojamiento().getId());
            ofertasTabla.setAll(ofertas);
            tablaOfertas.setItems(ofertasTabla);
            controlador.crearAlerta("Se ha agregado la oferta a la lista", Alert.AlertType.INFORMATION);
            tablaOfertas.getSelectionModel().clearSelection();
            limpiarCampos();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void editarBtnAction(ActionEvent event) {
        Oferta ofertaAntigua = tablaOfertas.getSelectionModel().getSelectedItem();
        if (ofertaAntigua == null) {
            controlador.crearAlerta("Debes seleccionar una oferta", Alert.AlertType.WARNING);
            return;
        }
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        LocalDate inicio = inicioDate.getValue();
        LocalDate fin = finDate.getValue();
        String porcentajeDescuento = descuentoField.getText();
        try {
            controlador.getEmpresa().getModuloComercialServicios().editarOferta(ofertaAntigua.getId(), nombre,
                    descripcion, inicio, fin, porcentajeDescuento);
            ofertas = controlador.getEmpresa().getModuloComercialServicios()
                    .obtenerOfertasAlojamiento(controlador.getAlojamientoSelect().getAlojamiento().getId());
            ofertasTabla.setAll(ofertas);
            tablaOfertas.setItems(ofertasTabla);
            controlador.crearAlerta("Se ha editado correctamente la oferta", Alert.AlertType.INFORMATION);
            tablaOfertas.getSelectionModel().clearSelection();
            limpiarCampos();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void eliminarBtnAction(ActionEvent event) {
        Oferta oferta = tablaOfertas.getSelectionModel().getSelectedItem();
        if (oferta == null) {
            controlador.crearAlerta("Debes seleccionar una oferta", Alert.AlertType.WARNING);
            return;
        }
        try {
            controlador.getEmpresa().getModuloComercialServicios().eliminarOferta(oferta.getId());
            ofertas.remove(oferta);
            ofertasTabla.setAll(ofertas);
            tablaOfertas.setItems(ofertasTabla);
            controlador.crearAlerta("Se ha eliminado la oferta de la lista", Alert.AlertType.INFORMATION);
            tablaOfertas.getSelectionModel().clearSelection();
            limpiarCampos();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        nombreField.clear();
        inicioDate.setValue(null);
        finDate.setValue(null);
        tipoOfertaBox.getSelectionModel().clearSelection();
        descuentoField.clear();
        descripcionField.clear();
    }
}
