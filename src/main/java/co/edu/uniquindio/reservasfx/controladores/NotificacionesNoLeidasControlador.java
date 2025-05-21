package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class NotificacionesNoLeidasControlador {

    @FXML
    private Button anteriorBtn;

    @FXML
    private Button siguienteBtn;

    @FXML private StackPane primerStack, segundoStack, tercerStack, cuartoStack, quintoStack;

    @FXML private Label numeroPaginalbl;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    private final static int MAX_NOTIFICACIONES_POR_PAGINA = 5;
    private int paginaActual, totalPaginas;

    private ArrayList<Notificacion> notificaciones;
    private ArrayList<Notificacion> notificacionesPagina;

    private StackPane[] stacks;

    @FXML
    public void initialize() {
        try {
            stacks = new StackPane[]{primerStack, segundoStack, tercerStack, cuartoStack, quintoStack};
            String cedula = controlador.getSesion().getUsuario().getCedula();
            notificaciones = controlador.getEmpresa().getModuloUsuarioServicios().obtenerNotificacionesNoLeidas(cedula);

            paginaActual = 1;

            cargarNotificacionesPagina();
        } catch (Exception e) {
            controlador.crearAlerta("Error cargando notificaciones: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarNotificacionesPagina() {
        notificacionesPagina = determinarListaPagina(paginaActual, MAX_NOTIFICACIONES_POR_PAGINA, notificaciones);
        cargarListaNotificaciones(notificacionesPagina, stacks);
        totalPaginas = notificaciones.size() / MAX_NOTIFICACIONES_POR_PAGINA;
        totalPaginas = totalPaginas + (notificaciones.size() % MAX_NOTIFICACIONES_POR_PAGINA == 0 ? 0 : 1);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    @FXML
    void siguienteBtnAction(ActionEvent event) {
        if (paginaActual < totalPaginas) {
            paginaActual++;
            cargarNotificacionesPagina();
        }
    }

    @FXML
    void anteriorBtnAction(ActionEvent event) {
        if (paginaActual > 1) {
            paginaActual--;
            cargarNotificacionesPagina();
        }
    }

    public ArrayList<Notificacion> determinarListaPagina(int pagina, int maxPorPagina, ArrayList<Notificacion> lista) {
        int inicio = (pagina - 1) * maxPorPagina;
        int fin = Math.min(inicio + maxPorPagina, lista.size());
        return new ArrayList<>(lista.subList(inicio, fin));
    }

    public void cargarListaNotificaciones(ArrayList<Notificacion> notificaciones, StackPane[] stacks) {
        for (int i = 0; i < stacks.length; i++) {
            StackPane stack = stacks[i];
            stack.getChildren().clear();

            if (i < notificaciones.size()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/reservasfx/notificacionCliente.fxml"));
                    Parent root = loader.load();
                    NotificacionClienteController controlador = loader.getController();
                    controlador.inicializarValores(notificaciones.get(i));
                    stack.getChildren().add(root);
                } catch (Exception e) {
                    controlador.crearAlerta("Error cargando notificación: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        }
    }

}
