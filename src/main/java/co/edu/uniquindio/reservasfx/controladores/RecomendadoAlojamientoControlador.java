package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.LinkedList;

public class RecomendadoAlojamientoControlador {
    @FXML
    private StackPane decimoStack;
    @FXML
    private Label numeroPaginalbl;
    @FXML
    private StackPane sextoStack;
    @FXML
    private StackPane novenoStack;
    @FXML
    private StackPane primerStack;
    @FXML
    private StackPane septimoStack;
    @FXML
    private StackPane cuartoStack;
    @FXML
    private Button paginaAnteriorBtn;
    @FXML
    private StackPane segundoStack;
    @FXML
    private Button siguientePaginaBtn;
    @FXML
    private StackPane tercerStack;
    @FXML
    private StackPane octavoStack;
    @FXML
    private StackPane quintoStack;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    ArrayList<Alojamiento> alojamientosCliente;
    ArrayList<Alojamiento> alojamientosOferta;
    ArrayList<Alojamiento> alojamientosPagina;

    String cedulaCliente;

    boolean esOferta = false;
    int paginaActual;
    int totalPaginas;

    @FXML
    void initialize() {
        try {
            paginaActual = 1;
            if (controlador.getSesion().getUsuario() == null) {
                alojamientosCliente = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerAlojamientosAleatorios();
                totalPaginas = alojamientosCliente.size() / 10;
                totalPaginas = totalPaginas + (alojamientosCliente.size() % 10 == 0 ? 0 : 1);
                numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
                alojamientosPagina = determinarAlojamientosPagina(alojamientosCliente);
                cargarListaAlojamientos(alojamientosPagina);
            } else {
                cedulaCliente = controlador.getSesion().getUsuario().getCedula();
                ArrayList<Reserva> reservasCliente = controlador.getEmpresa().getModuloComercialServicios().obtenerReservasCliente(cedulaCliente);
                alojamientosCliente = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerAlojamientosPreferenciasCliente(reservasCliente);
                totalPaginas = alojamientosCliente.size() / 10;
                totalPaginas = totalPaginas + (alojamientosCliente.size() % 10 == 0 ? 0 : 1);
                numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
                alojamientosPagina = determinarAlojamientosPagina(alojamientosCliente);
                cargarListaAlojamientos(alojamientosPagina);
            }

        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public ArrayList<Alojamiento> determinarAlojamientosPagina(ArrayList<Alojamiento> alojamientos) {
        ArrayList<Alojamiento> alojamientoArrayList = new ArrayList<>();
        try {
            int max = paginaActual * 10 - 1;
            int min = max - 9;
            alojamientoArrayList = controlador.getEmpresa()
                    .getModuloAlojamientoServicios()
                    .obtenerAlojamientosRango(min, max, alojamientos);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
        return alojamientoArrayList;
    }

    public void cargarListaAlojamientos(ArrayList<Alojamiento> alojamientos) {
        String rutaFXML = "/co/edu/uniquindio/reservasfx/alojamiento.fxml";
        StackPane[] stacks = {
                primerStack, segundoStack, tercerStack, cuartoStack, quintoStack,
                sextoStack, septimoStack, octavoStack, novenoStack, decimoStack
        };

        for (int i = 0; i < alojamientos.size() && i < stacks.length; i++) {
            cargarAlojamiento(rutaFXML, stacks[i], alojamientos.get(i));
        }
    }

    public void cargarAlojamiento(String rutaFXML, StackPane stack, Alojamiento alojamiento) {
        Parent node = cargarPanel(rutaFXML, alojamiento);
        stack.getChildren().setAll(node);
    }

    public Parent cargarPanel(String fxmlFile, Alojamiento alojamiento) {
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

    @FXML
    void paginaAnteriorAction(ActionEvent event) {
        if (paginaActual == 1) return;

        paginaActual--;
        alojamientosPagina = determinarAlojamientosPagina(alojamientosCliente);
        cargarListaAlojamientos(alojamientosPagina);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    @FXML
    void siguientePaginaAction(ActionEvent event) {
        if (paginaActual == totalPaginas) return;

        paginaActual++;
        alojamientosPagina = determinarAlojamientosPagina(alojamientosCliente);
        cargarListaAlojamientos(alojamientosPagina);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    public void inicializarValores() {
        try {
            ArrayList<Oferta> ofertas = controlador.getEmpresa().getModuloComercialServicios().obtenerOfertas();
            alojamientosOferta = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerAlojamientosOfertados(ofertas);
            totalPaginas = alojamientosOferta.size() / 10;
            totalPaginas = totalPaginas + (alojamientosOferta.size() % 10 == 0 ? 0 : 1);
            numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
            alojamientosPagina = determinarAlojamientosPagina(alojamientosOferta);
            cargarListaAlojamientos(alojamientosPagina);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
