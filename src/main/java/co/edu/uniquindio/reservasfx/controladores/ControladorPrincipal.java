package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.Sesion;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.servicios.EmpresaServicio;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ControladorPrincipal {

    private static ControladorPrincipal instancia;

    @Getter
    private final EmpresaServicio empresa;
    @Getter
    private final Sesion sesion;
    @Getter
    private final AlojamientoSelect alojamientoSelect;

    private ControladorPrincipal() {
        empresa = new EmpresaServicio();
        sesion = Sesion.getInstancia();
        alojamientoSelect = AlojamientoSelect.getInstancia();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            cargarVentana(tituloVentana, root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navegarVentanaActivacionCuenta(Node nodo, String nombreArchivoFxml, String tituloVentana, String cedulaUs, String correoUs) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();
            ActivacionCuentaControlador activacionCuentaControlador = loader.getController();
            activacionCuentaControlador.inicializarValores(cedulaUs, correoUs);

            cargarVentana(tituloVentana, root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navegarVentanaRecuperacionCuenta(Node nodo, String nombreArchivoFxml, String tituloVentana, String correoUs) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();
            CambioContraseniaControlador cambioContraseniaControlador = loader.getController();
            cambioContraseniaControlador.inicializarValores(correoUs);

            cargarVentana(tituloVentana, root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarVentana(String tituloVentana, Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(tituloVentana);
        stage.showAndWait();
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

    public void seleccionarImagen(ImageView imagen) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            try {
                Image imagenSeleccionada = new Image(archivoSeleccionado.toURI().toString());
                imagen.setImage(imagenSeleccionada);
            } catch (Exception e) {
                crearAlerta("No se pudo cargar la imagen seleccionada.", Alert.AlertType.ERROR);
            }
        }
    }

    public void cargarEnTab(Tab tab, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Node contenido = loader.load();
            tab.setContent(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Alojamiento> determinarAlojamientosPagina(int paginaActual, int cantidadStackPane, ArrayList<Alojamiento> alojamientos) {
        ArrayList<Alojamiento> alojamientoArrayList = new ArrayList<>();
        try {
            int max = paginaActual * cantidadStackPane - 1;
            int min = max - cantidadStackPane + 1;
            alojamientoArrayList = getEmpresa()
                    .getModuloAlojamientoServicios()
                    .obtenerAlojamientosRango(min, max, alojamientos);
        } catch (Exception e) {
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
        return alojamientoArrayList;
    }

    public void cargarListaAlojamientos(ArrayList<Alojamiento> alojamientos, StackPane[] stacks) {
        String rutaFXML = "/co/edu/uniquindio/reservasfx/alojamiento.fxml";

        for (int i = 0; i < alojamientos.size() && i < stacks.length; i++) {
            cargarAlojamiento(rutaFXML, stacks[i], alojamientos.get(i));
        }
    }

    public void cargarAlojamiento(String rutaFXML, StackPane stack, Alojamiento alojamiento) {
        Parent node = cargarPanelAlojamiento(rutaFXML, alojamiento);
        stack.getChildren().setAll(node);
    }

    public Parent cargarPanelAlojamiento(String fxmlFile, Alojamiento alojamiento) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent node = loader.load();
            AlojamientoControlador alojamientoControlador = loader.getController();
            alojamientoControlador.inicializarValores(alojamiento);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
