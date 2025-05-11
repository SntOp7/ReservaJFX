package co.edu.uniquindio.reservasfx.servicios.modulo.usuario;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.AlojamientoRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.CalificacionRepositorio;

import java.util.ArrayList;

public class CalificacionServicios {

    private final CalificacionRepositorio calificacionRepositorio;
    public ArrayList<Calificacion> calificaciones;
    public ArrayList<Alojamiento> alojamientos;

    public CalificacionServicios() {
        calificacionRepositorio = new CalificacionRepositorio();
    }

    public void registrarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception {
        if (nombreCliente == null || nombreCliente.isEmpty()) throw new Exception("El nombre del cliente es obligatorio");
        if (nombreAlojamiento == null || nombreAlojamiento.isEmpty()) throw new Exception("El nombre del alojamiento es obligatorio");
        if (comentario == null || comentario.isEmpty()) throw new Exception("El comentario es obligatorio");
        if (valoracion == 0) throw new Exception("La valoracion debe ser mayor a cero");

        Calificacion calificacion = new Calificacion(nombreCliente, nombreAlojamiento, comentario, valoracion);
        calificacionRepositorio.agregar(calificacion);
    }

    public ArrayList<Calificacion> obtenerCalificacionesAlojamiento(String idAlojamiento) throws Exception {
        if (idAlojamiento == null || idAlojamiento.isEmpty()) {
            throw new Exception("El ID del alojamiento es obligatorio");
        }

        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getId().equals(idAlojamiento))
                .findFirst()
                .orElse(null);

        if (alojamiento == null) {
            throw new Exception("El alojamiento no existe");
        }

        String nombreAlojamiento = alojamiento.getNombre();

        ArrayList<Calificacion> resultado = new ArrayList<>();
        for (Calificacion calificacion : calificaciones) {
            if (calificacion.getNombreAlojamiento().equals(nombreAlojamiento)) {
                resultado.add(calificacion);
            }
        }

        return resultado;
    }
}
