package co.edu.uniquindio.reservasfx.servicios.modulo.usuario;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import co.edu.uniquindio.reservasfx.repositorios.NotificacionRepositorio;
import co.edu.uniquindio.reservasfx.servicios.ModuloUsuarioServicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class NotificacionServicios {

    private final NotificacionRepositorio notificacionRepositorio;
    private ArrayList<Notificacion> notificaciones;

    public NotificacionServicios() {
        notificacionRepositorio = new NotificacionRepositorio();
    }

    public void registrarNotificacion(String cedulaCliente, String titulo, String mensaje, LocalDateTime fecha, Boolean leida) throws Exception{
        if (cedulaCliente == null || cedulaCliente.isEmpty()) throw new Exception("La cedula del cliente es obligatoria");
        if (titulo == null || titulo.isEmpty()) throw new Exception("El titulo es obligatorio");
        if (mensaje == null || mensaje.isEmpty()) throw new Exception("El mensaje es obligatorio");

        LocalDateTime fechaActual = LocalDateTime.now();
        Boolean leido = false;

        Notificacion notificacion = Notificacion.builder().cedulaCliente(cedulaCliente).titulo(titulo).mensaje(mensaje).fecha(fechaActual).leida(leido).build();
        notificacionRepositorio.agregar(notificacion);
    }

    public ArrayList<Notificacion> obtenerNotificacionesCLiente(String cedulaCliente) throws Exception{
        if (cedulaCliente == null || cedulaCliente.isEmpty()) {
            throw new Exception("La cédula del cliente es obligatoria");
        }

        ArrayList<Notificacion> resultado = new ArrayList<>();

        for (Notificacion notificacion : notificaciones) {
            if (notificacion.getCedulaCliente().equals(cedulaCliente)) {
                resultado.add(notificacion);
            }
        }

        return resultado;
    }
}
