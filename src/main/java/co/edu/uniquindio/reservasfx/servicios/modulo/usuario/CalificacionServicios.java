package co.edu.uniquindio.reservasfx.servicios.modulo.usuario;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.CalificacionRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class CalificacionServicios {

    private final CalificacionRepositorio calificacionRepositorio;
    private final NotificacionServicios notificacionServicios;
    private final AlojamientoServicios alojamientoServicios;

    public CalificacionServicios(NotificacionServicios notificacionServicios, AlojamientoServicios alojamientoServicios) {
        calificacionRepositorio = new CalificacionRepositorio();
        this.notificacionServicios = notificacionServicios;
        this.alojamientoServicios = alojamientoServicios;
    }

    public void enviarCalificacion(String cedulaCliente, String idAlojamiento, String comentario, String valoracion,
                                   ArrayList<Reserva> reservasCliente) throws Exception {

        Alojamiento alojamiento = alojamientoServicios.buscarAlojamientoPorId(idAlojamiento);
        if (comentario == null || comentario.trim().isEmpty()) throw new Exception("El comentario es obligatorio");
        if (valoracion == null || valoracion.isEmpty()) throw new  Exception("El valoracion es obligatoria");
        int valoraciones = Integer.parseInt(valoracion);
        if (valoraciones < 0 || valoraciones > 5) throw new Exception("La valoración debe estar entre 0 y 5");

        if (!clienteTuvoReservaCompletadaEnAlojamiento(reservasCliente, idAlojamiento)) {
            throw new Exception("Solo puedes realizar una reseña si ya has completado una reserva en este alojamiento");
        }

        Calificacion calificacion = new Calificacion(cedulaCliente, idAlojamiento, comentario, valoraciones);
        calificacionRepositorio.agregar(calificacion);
        notificacionServicios.enviarNotificacion(cedulaCliente, "Reseña Publicada",
                Constantes.CALIFICACION_PUBLICADA(alojamiento.getNombre()));
    }

    public boolean clienteTuvoReservaCompletadaEnAlojamiento(ArrayList<Reserva> reservasCliente, String idAlojamiento) {
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
