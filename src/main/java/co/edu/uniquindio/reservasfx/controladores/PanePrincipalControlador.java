package co.edu.uniquindio.reservasfx.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class PanePrincipalControlador {
    @FXML
    private StackPane inicioInferiorStack;
    @FXML
    private StackPane inicioSuperiorStack;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    private static PanePrincipalControlador instancia;

    private PanePrincipalControlador() {}

    public static PanePrincipalControlador getInstancia(){
        if(instancia == null){
            instancia = new PanePrincipalControlador();
        }
        return instancia;
    }

    @FXML
    private void initialize() {
        try {
            controlador.getEmpresa().getModuloComercialServicios().actualizarEstadoOfertas();
            controlador.getEmpresa().getModuloComercialServicios().actualizarEstadoReservas();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
        actualizar("/co/edu/uniquindio/reservasfx/headerPrincipal.fxml",
                "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
    }

    public void actualizarInferior(String fxmlFile) {
        inicioInferiorStack.getChildren().clear();
        Parent node = controlador.cargarPanel(fxmlFile);
        inicioInferiorStack.getChildren().add(node);
    }

    public void actualizarInferiorPersonalizadoFiltrado(String fxmlFile, String titulo) {
        inicioInferiorStack.getChildren().clear();
        Parent node = cargarPanelPerFiltrado(fxmlFile, titulo);
        inicioInferiorStack.getChildren().add(node);
    }

    private Parent cargarPanelPerFiltrado(String fxmlFile, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent node = loader.load();
            FiltradoAlojamientoControlador filtradoAlojamientoControlador = loader.getController();
            filtradoAlojamientoControlador.inicializarValores(titulo);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarInferiorPersonalizadoOferta(String fxmlFile) {
        inicioInferiorStack.getChildren().clear();
        Parent node = cargarPanelPerOferta(fxmlFile);
        inicioInferiorStack.getChildren().add(node);
    }

    private Parent cargarPanelPerOferta(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent node = loader.load();
            RecomendadoAlojamientoControlador recomendadoAlojamientoControlador = loader.getController();
            recomendadoAlojamientoControlador.inicializarValores();
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizar(String fxmlFileSuperior, String fxmlFileInferior) {
        inicioSuperiorStack.getChildren().clear();
        inicioInferiorStack.getChildren().clear();
        Parent nodeSuperior = controlador.cargarPanel(fxmlFileSuperior);
        inicioSuperiorStack.getChildren().add(nodeSuperior);
        Parent nodeInferior = controlador.cargarPanel(fxmlFileInferior);
        inicioInferiorStack.getChildren().add(nodeInferior);
    }
}
