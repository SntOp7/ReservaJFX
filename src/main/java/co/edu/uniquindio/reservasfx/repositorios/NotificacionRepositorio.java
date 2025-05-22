package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class NotificacionRepositorio {
    private ArrayList<Notificacion> notificaciones;

    public NotificacionRepositorio() {
        this.notificaciones = leerDatos();
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

    public void guardarDatos(ArrayList<Notificacion> notificaciones) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_NOTIFICACIONES, notificaciones);
        } catch (IOException e) {
            System.err.println("Error guardando notificaciones: " + e.getMessage());
        }
    }


    public ArrayList<Notificacion> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_NOTIFICACIONES);
            if (datos != null) {
                return (ArrayList<Notificacion>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando notificaciones: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
