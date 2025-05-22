package co.edu.uniquindio.reservasfx.servicios.modulo.usuario;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import co.edu.uniquindio.reservasfx.repositorios.NotificacionRepositorio;
import co.edu.uniquindio.reservasfx.servicios.ModuloUsuarioServicios;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class NotificacionServicios {

    private final NotificacionRepositorio notificacionRepositorio;

    public NotificacionServicios() {
        notificacionRepositorio = new NotificacionRepositorio();
    }

    public void enviarNotificacion(String cedulaCliente, String titulo, String mensaje) throws Exception{
        if (titulo == null || titulo.isEmpty()) throw new Exception("El titulo es obligatorio");
        if (mensaje == null || mensaje.isEmpty()) throw new Exception("El mensaje es obligatorio");

        Notificacion notificacion = Notificacion.builder().cedulaCliente(cedulaCliente).titulo(titulo).
                mensaje(mensaje).fecha(LocalDateTime.now()).leida(false).build();
        notificacionRepositorio.agregar(notificacion);
    }

    public ArrayList<Notificacion> obtenerNotificacionesNoLeidas(String cedulaCliente) throws Exception {
       return notificacionRepositorio.obtenerNotificacionesPorCedula(cedulaCliente);
    }

    public void marcarNotificacionComoLeida(Notificacion notificacion) {
        notificacion.setLeida(true);
    }
}
