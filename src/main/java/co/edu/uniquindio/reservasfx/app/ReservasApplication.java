package co.edu.uniquindio.reservasfx.app;

import co.edu.uniquindio.reservasfx.controladores.ControladorPrincipal;
import co.edu.uniquindio.reservasfx.controladores.PanePrincipalControlador;
import co.edu.uniquindio.reservasfx.repositorios.*;
import co.edu.uniquindio.reservasfx.servicios.EmpresaServicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReservasApplication extends Application {

    ControladorPrincipal controlador = ControladorPrincipal.getInstancia();
    EmpresaServicio empresaAlojamiento = controlador.getEmpresa();
    AlojamientoRepositorio alojamientoRepositorio = empresaAlojamiento.getModuloAlojamientoServicios()
            .getAlojamientoServicios().getAlojamientoRepositorio();
    CalificacionRepositorio calificacionRepositorio = empresaAlojamiento.getModuloUsuarioServicios()
            .getCalificacionServicios().getCalificacionRepositorio();
    DeseoRepositorio deseoRepositorio = empresaAlojamiento.getModuloUsuarioServicios()
            .getUsuarioServicios().getDeseoRepositorio();
    HabitacionRepositorio habitacionRepositorio =  empresaAlojamiento.getModuloAlojamientoServicios()
            .getHabitacionServicios().getHabitacionRepositorio();
    ImagenRepositorio imagenRepositorio = empresaAlojamiento.getModuloAlojamientoServicios()
            .getAlojamientoServicios().getImagenRepositorio();
    NotificacionRepositorio notificacionRepositorio =  empresaAlojamiento.getModuloUsuarioServicios()
            .getNotificacionServicios().getNotificacionRepositorio();
    OfertaRepositorio ofertaRepositorio =  empresaAlojamiento.getModuloComercialServicios()
            .getOfertaServicios().getOfertaRepositorio();
    ReservaRepositorio reservaRepositorio =  empresaAlojamiento.getModuloComercialServicios()
            .getReservaServicios().getReservaRepositorio();
    ServicioRepositorio servicioRepositorio = empresaAlojamiento.getModuloAlojamientoServicios()
            .getAlojamientoServicios().getServicioRepositorio();
    UsuarioRepositorio usuarioRepositorio = empresaAlojamiento.getModuloUsuarioServicios()
            .getUsuarioServicios().getUsuarioRepositorio();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservasApplication.class.getResource(
                "/co/edu/uniquindio/reservasfx/panePrincipal.fxml"));
        loader.setController(PanePrincipalControlador.getInstancia());
        Parent parent = loader.load();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            alojamientoRepositorio.guardarDatos(alojamientoRepositorio.getAlojamientos());
            calificacionRepositorio.guardarDatos(calificacionRepositorio.getCalificaciones());
            deseoRepositorio.guardarDatos(deseoRepositorio.getDeseos());
            habitacionRepositorio.guardarDatos(habitacionRepositorio.getHabitaciones());
            imagenRepositorio.guardarDatos(imagenRepositorio.getImagenes());
            notificacionRepositorio.guardarDatos(notificacionRepositorio.getNotificaciones());
            ofertaRepositorio.guardarDatos(ofertaRepositorio.getOfertas());
            reservaRepositorio.guardarDatos(reservaRepositorio.getReservas());
            servicioRepositorio.guardarDatos(servicioRepositorio.getServicios());
            usuarioRepositorio.guardarDatosCl(usuarioRepositorio.getClientes());
            usuarioRepositorio.guardarDatosAd(usuarioRepositorio.getAdministradores());
            System.out.println("Datos guardados automáticamente al cerrar el programa.");
        }));

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