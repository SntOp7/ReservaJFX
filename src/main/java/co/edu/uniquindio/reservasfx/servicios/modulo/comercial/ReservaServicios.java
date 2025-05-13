package co.edu.uniquindio.reservasfx.servicios.modulo.comercial;

import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Factura;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;
import co.edu.uniquindio.reservasfx.repositorios.ReservaRepositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ReservaServicios {

    private final ReservaRepositorio reservaRepositorio;

    public ReservaServicios() {
        reservaRepositorio = new ReservaRepositorio();
    }

    public void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                                int numeroHuespedes) throws Exception {

        if (fechaInicio == null) throw new Exception("La fecha de inicio es obligatoria");
        if (fechaFin == null) throw new Exception("La fecha de fin es obligatoria");
        if (numeroHuespedes == 0) throw new Exception("El numero de huespedes debe ser mayor a 0");


    }

    public void cancelarReserva(String id) throws Exception {
        Reserva reserva = reservaRepositorio.buscarReservaPorId(id);
        if (reserva == null) throw new Exception("La reserva no existe");
        reservaRepositorio.cancelar(reserva);
    }

    public ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) {
    }

    public EstadisticasAlojamiento obtenerEstadisticasAlojamiento(String idAlojamiento) {
    }

    public EstadisticasTipoAlojamiento obtenerRentabilidadTipoAlojamiento(int mes) {
    }
}
