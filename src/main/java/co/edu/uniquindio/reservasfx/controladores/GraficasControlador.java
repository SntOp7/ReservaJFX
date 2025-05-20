package co.edu.uniquindio.reservasfx.controladores;

import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GraficasControlador{

    @FXML
    private BarChart<String, Number> barChartTipos;

    @FXML
    private PieChart pieChartTipos;

    @FXML
    private BarChart<String, Number> barChartAlojamientos;

    @FXML
    private PieChart pieChartAlojamientos;

    @FXML
    private VBox panelGraficas;


    // Método para graficar tipo de alojamiento
    public void graficarTipos(ArrayList<EstadisticasTipoAlojamiento> tipos) {
        barChartTipos.getData().clear();
        pieChartTipos.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (EstadisticasTipoAlojamiento tipoAlojamiento : tipos) {
            series.getData().add(new XYChart.Data<>(tipoAlojamiento.getTipo().toString(), tipoAlojamiento.getRentabilidadTipo()));
            pieChartTipos.getData().add(new PieChart.Data(tipoAlojamiento.getTipo().toString(), tipoAlojamiento.getRentabilidadTipo()));
        }

        barChartTipos.getData().add(series);
    }

    // Método para mostrar gráfico de pastel de ocupación
    public void mostrarGraficoOcupacion(EstadisticasAlojamiento estadisticas) {
        panelGraficas.getChildren().clear(); // Opcional: limpiar antes

        double ocupacion = estadisticas.getOcupacionPorcentual();
        double desocupacion = 100 - ocupacion;

        PieChart pieChart = new PieChart();
        pieChart.setTitle("Ocupación del Alojamiento");
        pieChart.getData().add(new PieChart.Data("Ocupado", ocupacion));
        pieChart.getData().add(new PieChart.Data("Disponible", desocupacion));

        panelGraficas.getChildren().add(pieChart);
    }

    // Método para mostrar gráfico de barras de ganancias
    public void mostrarGraficoGanancias(EstadisticasAlojamiento estadisticas) {
        panelGraficas.getChildren().clear(); // Opcional: limpiar antes

        double gananciaAlojamiento = estadisticas.getGananciasTotales();
        double gananciaGlobal = estadisticas.getGananciasTotalesGlobales();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Fuente");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ganancias ($)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Comparación de Ganancias");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ganancias");

        series.getData().add(new XYChart.Data<>("Alojamiento", gananciaAlojamiento));
        series.getData().add(new XYChart.Data<>("Global", gananciaGlobal));

        barChart.getData().add(series);

        panelGraficas.getChildren().add(barChart);
    }
}
