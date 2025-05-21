package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.AlojamientoSelect;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class EstadisticasAlojamientoControlador {

    @FXML
    private StackPane stackOcupacionAnual;

    @FXML
    private StackPane stackGananciaAnual;

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

    @FXML
    public void initialize() {
        try {
            EstadisticasAlojamiento estadisticas = controlador.getEmpresa().getModuloComercialServicios().obtenerEstadisticasAlojamiento(AlojamientoSelect.getInstancia().getAlojamiento().getId());
            cargarGraficas(estadisticas);
        } catch (Exception e) {
            controlador.crearAlerta("Error al cargar las gráficas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void cargarGraficas(EstadisticasAlojamiento estadisticas) {
        try {
            GraficasControlador graficasControlador = new GraficasControlador();

            PieChart graficoOcupacion = graficasControlador.generarGraficoOcupacion(estadisticas);
            BarChart<String, Number> graficoGanancias = graficasControlador.generarGraficoGanancias(estadisticas);

            stackOcupacionAnual.getChildren().clear();
            stackOcupacionAnual.getChildren().add(graficoOcupacion);

            stackGananciaAnual.getChildren().clear();
            stackGananciaAnual.getChildren().add(graficoGanancias);

        } catch (Exception e) {
            controlador.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
