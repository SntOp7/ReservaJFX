package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;

import java.util.ArrayList;

public class CalificacionRepositorio {

    private ArrayList<Calificacion> calificaciones;

    public CalificacionRepositorio() {
        calificaciones = new ArrayList<>();
    }

    public void agregar(Calificacion calificacion) {
        calificaciones.add(calificacion);
    }

    public ArrayList<Calificacion> obtenerCalificacionesPorNombre(String nombreAlojamiento) {
        ArrayList<Calificacion> calificacionesPorNombre = new ArrayList<>();
        for (Calificacion calificacion : calificaciones) {
            if (calificacion.getNombreAlojamiento().equals(nombreAlojamiento)) {
                calificacionesPorNombre.add(calificacion);
            }
        }
        return calificacionesPorNombre;
    }
}
