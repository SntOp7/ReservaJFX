package co.edu.uniquindio.reservasfx.controladores;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

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
        actualizar("/co/edu/uniquindio/reservasfx/headerPrincipal.fxml",
                "/co/edu/uniquindio/reservasfx/recomendadoAlojamiento.fxml");
    }

    public final void actualizarInferior(String fxmlFile) {
        inicioInferiorStack.getChildren().clear();
        Parent node = controlador.cargarPanel(fxmlFile);
        inicioInferiorStack.getChildren().add(node);
    }

    public void actualizarSuperior(String fxmlFile) {
        inicioSuperiorStack.getChildren().clear();
        Parent node = controlador.cargarPanel(fxmlFile);
        inicioSuperiorStack.getChildren().add(node);
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
