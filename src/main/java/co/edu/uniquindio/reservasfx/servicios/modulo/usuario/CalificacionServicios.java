package co.edu.uniquindio.reservasfx.servicios.modulo.usuario;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.AlojamientoRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.CalificacionRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;

import java.util.ArrayList;

public class CalificacionServicios {

    private final CalificacionRepositorio calificacionRepositorio;

    public CalificacionServicios() {
        calificacionRepositorio = new CalificacionRepositorio();
    }

    public void enviarCalificacion(String cedulaCliente, String idAlojamiento, String comentario, int valoracion,
                                   ArrayList<Reserva> reservasCliente) throws Exception {

        if (comentario == null || comentario.trim().isEmpty()) throw new Exception("El comentario es obligatorio");
        if (valoracion < 0 || valoracion > 5) throw new Exception("La valoración debe estar entre 0 y 5");

        if (!clienteTuvoReservaCompletadaEnAlojamiento(reservasCliente, idAlojamiento)) {
            throw new Exception("Solo puedes realizar una reseña si ya has completado una reserva en este alojamiento");
        }

        Calificacion calificacion = new Calificacion(cedulaCliente, idAlojamiento, comentario, valoracion);
        calificacionRepositorio.agregar(calificacion);
    }

    private boolean clienteTuvoReservaCompletadaEnAlojamiento(ArrayList<Reserva> reservasCliente, String idAlojamiento) {
        return reservasCliente.stream()
                .anyMatch(r ->
                        r.getIdAlojamiento().equals(idAlojamiento) &&
                                r.getEstado() == EstadoReserva.FINALIZADA
                );
    }

    public ArrayList<Calificacion> obtenerCalificacionesAlojamiento(String idAlojamiento) throws Exception {
        return calificacionRepositorio.obtenerCalificacionesPorId(idAlojamiento);
    }
}
