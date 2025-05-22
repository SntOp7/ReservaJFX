package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.repositorios.AlojamientoRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.reservasfx.servicios.EmpresaServicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PanePrincipalControlador implements Initializable {
    @FXML
    private StackPane inicioInferiorStack;
    @FXML
    private StackPane inicioSuperiorStack;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    EmpresaServicio empresaAlojamiento = controlador.getEmpresa();
    AlojamientoRepositorio alojamientoRepositorio = controlador.getEmpresa().getModuloAlojamientoServicios().getAlojamientoServicios().getAlojamientoRepositorio();

    private static PanePrincipalControlador instancia;

    private PanePrincipalControlador() {}

    public static PanePrincipalControlador getInstancia() {
        if(instancia == null){
            instancia = new PanePrincipalControlador();
        }
        return instancia;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            controlador.getEmpresa().getModuloComercialServicios().actualizarEstadoOfertas();
            controlador.getEmpresa().getModuloComercialServicios().actualizarEstadoReservas();
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
        actualizar("/co/edu/uniquindio/reservasfx/headerPrincipal.fxml",
                "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            System.out.println("Datos guardados automáticamente al cerrar el programa.");
        }));
    }

    public void limpiarInferior() {
        inicioInferiorStack.getChildren().clear();
    }

    public void actualizarInferior(String fxmlFile) {
        inicioInferiorStack.getChildren().clear();
        Parent node = controlador.cargarPanel(fxmlFile);
        inicioInferiorStack.getChildren().add(node);
    }

    public void actualizarInferiorPersonalizadoFiltrado(String fxmlFile, String titulo, TipoAlojamiento tipoAlojamiento) {
        inicioInferiorStack.getChildren().clear();
        Parent node = cargarPanelPerFiltrado(fxmlFile, titulo, tipoAlojamiento);
        inicioInferiorStack.getChildren().add(node);
    }

    private Parent cargarPanelPerFiltrado(String fxmlFile, String titulo, TipoAlojamiento tipoAlojamiento ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent node = loader.load();
            FiltradoAlojamientoControlador filtradoAlojamientoControlador = loader.getController();
            filtradoAlojamientoControlador.inicializarValores(titulo, tipoAlojamiento);
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
