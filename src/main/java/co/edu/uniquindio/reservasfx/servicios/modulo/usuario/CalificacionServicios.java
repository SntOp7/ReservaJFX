package co.edu.uniquindio.reservasfx.servicios.modulo.usuario;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.AlojamientoRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.CalificacionRepositorio;

import java.util.ArrayList;

public class CalificacionServicios {

    private final CalificacionRepositorio calificacionRepositorio;

    public CalificacionServicios() {
        calificacionRepositorio = new CalificacionRepositorio();
    }

    public void enviarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception {
        if (comentario == null || comentario.isEmpty()) throw new Exception("El comentario es obligatorio");
        if (valoracion < 0 || valoracion > 5) throw new Exception("El valoracion debe estar entre 0 y 5");

        Calificacion calificacion = new Calificacion(nombreCliente, nombreAlojamiento, comentario, valoracion);
        calificacionRepositorio.agregar(calificacion);
    }

    public ArrayList<Calificacion> obtenerCalificacionesAlojamiento(String nombreAlojamiento) {
        return calificacionRepositorio.obtenerCalificacionesPorNombre(nombreAlojamiento);
    }
}
