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
}
