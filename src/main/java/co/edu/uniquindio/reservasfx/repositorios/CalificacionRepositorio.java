package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class CalificacionRepositorio {
    private ArrayList<Calificacion> calificaciones;

    public CalificacionRepositorio() {
        this.calificaciones = leerDatos();
        System.out.println("Calificaciones cargados desde archivo: " + calificaciones.size());
    }

    public void agregar(Calificacion calificacion) {
        calificaciones.add(calificacion);
    }

    public ArrayList<Calificacion> obtenerCalificacionesPorId(String id) {
        ArrayList<Calificacion> calificacionesPorId = new ArrayList<>();
        for (Calificacion calificacion : calificaciones) {
            if (calificacion.getIdAlojamiento().equals(id)) {
                calificacionesPorId.add(calificacion);
            }
        }
        return calificacionesPorId;
    }

    public void guardarDatos(ArrayList<Calificacion> calificaciones) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_CALIFICACIONES, calificaciones);
            System.out.println("Calificaciones guardados en archivo: " + calificaciones.size());
        } catch (IOException e) {
            System.err.println("Error guardando calificaciones: " + e.getMessage());
        }
    }


    public ArrayList<Calificacion> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_CALIFICACIONES);
            if (datos != null) {
                return (ArrayList<Calificacion>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando calificaciones: " + e.getMessage());
        }
        return new ArrayList<>();
    }

}
