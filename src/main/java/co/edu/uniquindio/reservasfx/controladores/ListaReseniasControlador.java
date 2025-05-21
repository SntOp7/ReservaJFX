package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Administrador;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;

public class ListaReseniasControlador {

    @FXML
    private TextField comentarioTxt;

    @FXML
    private TextField valoracionTxt;

    @FXML
    private Button anteriorBtn, siguienteBtn, enviarReseniaBtn;

    @FXML
    private VBox vboxResenias;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    @FXML private StackPane primerStack;
    @FXML private StackPane segundoStack;
    @FXML private StackPane tercerStack;
    @FXML private StackPane cuartoStack;
    @FXML private StackPane quintoStack;
    @FXML private Label numeroPaginalbl;

    Alojamiento alojamiento = AlojamientoSelect.getInstancia().getAlojamiento();

    ArrayList<Calificacion> resenias;
    ArrayList<Calificacion> reseniasPagina;
    int paginaActual;
    int totalPaginas;

    @FXML
    void initialize() {
        try {
            resenias = controlador.getEmpresa().getModuloUsuarioServicios().obtenerCalificacionesAlojamiento(alojamiento.getId());

            paginaActual = 1;
            totalPaginas = resenias.size() / 5 + (resenias.size() % 5 == 0 ? 0 : 1);
            numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
            reseniasPagina = determinarReseniasPagina(resenias);
            cargarListaResenias(reseniasPagina);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void enviarReseniaBtnAction(ActionEvent event) {
        try {
            String comentario = comentarioTxt.getText();
            String valoracion = valoracionTxt.getText();

            String idAlojamiento = alojamiento.getId();
            String cedulaCliente = controlador.getSesion().getUsuario().getCedula();

            boolean Uadmin = controlador.getSesion().getUsuario() instanceof Administrador;
            if (Uadmin) controlador.crearAlerta("El administrador no puede agregar reseñas",  Alert.AlertType.ERROR);

            ArrayList<Reserva> reservasCliente = controlador.getEmpresa().getModuloComercialServicios().obtenerReservasCliente(cedulaCliente);

            controlador.getEmpresa().enviarCalificacion(cedulaCliente, idAlojamiento, comentario, valoracion, reservasCliente);

            resenias = controlador.getEmpresa().getModuloUsuarioServicios().obtenerCalificacionesAlojamiento(idAlojamiento);

            paginaActual = 1;
            totalPaginas = resenias.size() / 5 + (resenias.size() % 5 == 0 ? 0 : 1);
            reseniasPagina = determinarReseniasPagina(resenias);
            cargarListaResenias(reseniasPagina);
            numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);

            comentarioTxt.clear();
            valoracionTxt.clear();

            controlador.crearAlerta("Reseña enviada con éxito", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controlador.crearAlerta("Error al enviar la reseña: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public ArrayList<Calificacion> determinarReseniasPagina(ArrayList<Calificacion> todasLasResenias) {
        ArrayList<Calificacion> reseniasPagina = new ArrayList<>();
        int max = paginaActual * 5;
        int min = max - 5;

        if (min >= todasLasResenias.size()) {
            return reseniasPagina;
        }

        if (max > todasLasResenias.size()) {
            max = todasLasResenias.size();
        }

        reseniasPagina.addAll(todasLasResenias.subList(min, max));
        return reseniasPagina;
    }

    public void cargarListaResenias(ArrayList<Calificacion> resenias) {
        String rutaFXML = "/co/edu/uniquindio/reservasfx/resenia.fxml";
        StackPane[] stacks = {
                primerStack, segundoStack, tercerStack, cuartoStack, quintoStack
        };

        for (int i = 0; i < resenias.size() && i < stacks.length; i++) {
            cargarResenia(rutaFXML, stacks[i], resenias.get(i));
        }

        // Limpiar los StackPane restantes si no hay suficientes reseñas
        for (int i = resenias.size(); i < stacks.length; i++) {
            stacks[i].getChildren().clear();
        }
    }

    public void cargarResenia(String rutaFXML, StackPane stack, Calificacion resenia) {
        Parent node = cargarPanel(rutaFXML, resenia);
        stack.getChildren().setAll(node);
    }

    public Parent cargarPanel(String fxmlFile, Calificacion calificacion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent node = loader.load();
            ReseniaClienteControlador controlador = loader.getController();
            controlador.inicializarValores(calificacion);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void anteriorBtnAction(ActionEvent event) {
        if (paginaActual == 1) {
            controlador.crearAlerta("Ya estás en la primera página", Alert.AlertType.INFORMATION);
        } else {
            paginaActual--;
            reseniasPagina = determinarReseniasPagina(resenias);
            cargarListaResenias(reseniasPagina);
            numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
        }
    }

    @FXML
    void siguienteBtnAction(ActionEvent event) {
        if (paginaActual == totalPaginas) {
            controlador.crearAlerta("Ya estás en la última página", Alert.AlertType.INFORMATION);
        } else {
            paginaActual++;
            reseniasPagina = determinarReseniasPagina(resenias);
            cargarListaResenias(reseniasPagina);
            numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
        }
    }
}


