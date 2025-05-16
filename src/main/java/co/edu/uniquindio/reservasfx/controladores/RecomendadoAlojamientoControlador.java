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

    final static int MAX_ALOJAMIENTOS_POR_PAGINA = 10;
    StackPane[] stacks = {primerStack, segundoStack, tercerStack, cuartoStack, quintoStack,
            sextoStack, novenoStack, decimoStack, septimoStack, octavoStack};
    int paginaActual;
    int totalPaginas;

    @FXML
    void initialize() {
        try {
            paginaActual = 1;
            if (controlador.getSesion().getUsuario() == null) {
                alojamientosCliente = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerAlojamientosAleatorios();
                cargarDatosPanelConAlojamientos(alojamientosCliente);
            } else {
                cedulaCliente = controlador.getSesion().getUsuario().getCedula();
                ArrayList<Reserva> reservasCliente = controlador.getEmpresa().getModuloComercialServicios().obtenerReservasCliente(cedulaCliente);
                alojamientosCliente = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerAlojamientosPreferenciasCliente(reservasCliente);
                cargarDatosPanelConAlojamientos(alojamientosCliente);
            }
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosPanelConAlojamientos(ArrayList<Alojamiento> alojamientos) {
        totalPaginas = alojamientos.size() / MAX_ALOJAMIENTOS_POR_PAGINA;
        totalPaginas = totalPaginas + (alojamientos.size() % MAX_ALOJAMIENTOS_POR_PAGINA == 0 ? 0 : 1);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
        alojamientosPagina = controlador.determinarAlojamientosPagina(paginaActual, MAX_ALOJAMIENTOS_POR_PAGINA, alojamientos);
        controlador.cargarListaAlojamientos(alojamientosPagina, stacks);
    }

    @FXML
    void paginaAnteriorAction(ActionEvent event) {
        if (paginaActual == 1) return;

        paginaActual--;
        alojamientosPagina = controlador.determinarAlojamientosPagina(paginaActual, MAX_ALOJAMIENTOS_POR_PAGINA, alojamientosCliente);
        controlador.cargarListaAlojamientos(alojamientosPagina, stacks);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    @FXML
    void siguientePaginaAction(ActionEvent event) {
        if (paginaActual == totalPaginas) return;

        paginaActual++;
        alojamientosPagina = controlador.determinarAlojamientosPagina(paginaActual, MAX_ALOJAMIENTOS_POR_PAGINA, alojamientosCliente);
        controlador.cargarListaAlojamientos(alojamientosPagina, stacks);
        numeroPaginalbl.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    public void inicializarValores() {
        try {
            ArrayList<Oferta> ofertas = controlador.getEmpresa().getModuloComercialServicios().obtenerOfertas();
            alojamientosOferta = controlador.getEmpresa().getModuloAlojamientoServicios().obtenerAlojamientosOfertados(ofertas);
            cargarDatosPanelConAlojamientos(alojamientosOferta);
        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
