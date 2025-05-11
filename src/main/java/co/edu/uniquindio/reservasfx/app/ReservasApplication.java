package co.edu.uniquindio.reservasfx.app;

import co.edu.uniquindio.reservasfx.controladores.PanePrincipalControlador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReservasApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservasApplication.class.getResource(
                "/co/edu/uniquindio/reservasfx/panePrincipal.fxml"));
        loader.setController(PanePrincipalControlador.getInstancia());
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Reservación de Alojamientos");
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(ReservasApplication.class, args);
    }
}