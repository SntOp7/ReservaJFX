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
}
