package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoOferta;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ListaOfertasControlador {
    @FXML
    private Label descuentoLbl;
    @FXML
    private TableColumn<Oferta, String> fechaInicioColumn;
    @FXML
    private Label descripcionLbl;
    @FXML
    private Label tipoOfertaLbl;
    @FXML
    private TableColumn<Oferta, String> fechaFinColumn;
    @FXML
    private TableColumn<Oferta, String> nombreColumn;
    @FXML
    private TableView<Oferta> tablaOfertas;
    @FXML
    private TableColumn<Oferta, String> estadoColumn;

    private final ObservableList<Oferta> listaOfertas = FXCollections.observableArrayList();

    private Alojamiento alojamiento = AlojamientoSelect.getInstancia().getAlojamiento();

    private final ControladorPrincipal controlador = ControladorPrincipal.getInstancia();


    @FXML
    public void initialize() {
        nombreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        fechaInicioColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaInicio().toString()));
        fechaFinColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaFin().toString()));
        estadoColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado().getNombre()));

        cargarOfertas();

        tablaOfertas.setItems(listaOfertas);

        tablaOfertas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tipoOfertaLbl.setText("Tipo de Oferta: " + newSelection.getOfertaEspecial().getNombre());
                descripcionLbl.setText("Descripción: " + newSelection.getDescripcion());
                descuentoLbl.setText("Descuento: " + newSelection.getPorcentajeDescuento() + "%");
            }
        });
    }

    private void cargarOfertas() {
        try {
            if (alojamiento != null) {
                String idAlojamiento = alojamiento.getId();
                ArrayList<Oferta> ofertas = controlador.getEmpresa().getModuloComercialServicios()
                        .obtenerOfertasAlojamiento(idAlojamiento);
                for (Oferta oferta : ofertas) {
                    if (oferta.getEstado() != EstadoOferta.CADUCADA) {
                        listaOfertas.add(oferta);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando ofertas: " + e.getMessage());
        }
    }

}
