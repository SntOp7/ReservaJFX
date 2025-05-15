package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.servicios.EmpresaServicio;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.net.URL;

public class ControladorPrincipal {

    private static ControladorPrincipal instancia;

    @Getter
    private final EmpresaServicio empresa;
    @Getter
    private final Sesion sesion;

    private ControladorPrincipal() {
        empresa = new EmpresaServicio();
        sesion = Sesion.getInstancia();
    }

    public static ControladorPrincipal getInstancia(){
        if(instancia == null){
            instancia = new ControladorPrincipal();
        }
        return instancia;
    }

    public void cerrarVentana(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Parent cargarPanel(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent node = loader.load();
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void navegarVentana(Node nodo, String nombreArchivoFxml, String tituloVentana) {
        try {
            // Cerrar ventana actual
            Stage stageClose = (Stage) nodo.getScene().getWindow();
            stageClose.close();

            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear y mostrar nueva ventana
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle(tituloVentana);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarImagen(String imagePath, ImageView imageView) {
        if (imagePath == null || imageView == null) {
            return;
        }

        if (imagePath.startsWith("/") || imagePath.startsWith("resources/")) {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                imageView.setImage(new Image(imageUrl.toExternalForm()));
            }
        } else {
            File file = new File(imagePath);
            if (file.exists()) {
                imageView.setImage(new Image(file.toURI().toString()));
            }
        }
    }
}
