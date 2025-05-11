package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;

import java.util.ArrayList;

public class NotificacionRepositorio {
    private ArrayList<Notificacion> notificaciones;

    public NotificacionRepositorio() {
        notificaciones = new ArrayList<>();
    }

    public void agregar(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }

    public ArrayList<Notificacion> obtenerNotificacionesPorCedula(String cedulaCliente) {
        ArrayList<Notificacion> notificacionesPorCedula = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            if (notificacion.getCedulaCliente().equals(cedulaCliente) && !notificacion.isLeida()) {
                notificacionesPorCedula.add(notificacion);
            }
        }
        return notificacionesPorCedula;
    }
}
