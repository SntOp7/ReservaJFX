package co.edu.uniquindio.reservasfx.servicios.modulo.comercial;

import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.ReservaRepositorio;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservaServicios {

    private final ReservaRepositorio reservaRepositorio;

    public ReservaServicios() {
        reservaRepositorio = new ReservaRepositorio();
    }

    public void registrarReserva(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, int numeroHuespedes) throws Exception {
        if (cliente == null ) throw new Exception("El cliente es obligatorio");
        if (alojamiento == null) throw new Exception("El alojamiento es obligatorio");
        if (fechaInicio == null) throw new Exception("La fecha de inicio es obligatoria");
        if (fechaFin == null) throw new Exception("La fecha de fin es obligatoria");
        if (numeroHuespedes == 0) throw new Exception("El numero de huespedes debe ser mayor a 0");

        Reserva reserva = Reserva.builder().cliente(cliente).alojamiento(alojamiento).fechaInicio(fechaInicio).fechaFin(fechaFin).numeroHuespedes(numeroHuespedes).build();

        reservaRepositorio.agregar(reserva);
    }

    public void cancelarReserva(String cedulaCliente) throws Exception {
        Reserva reserva = buscarReserva(cedulaCliente);
        if (reserva == null) throw new Exception("La reserva no existe");
        reservaRepositorio.cancelar(reserva);
    }

    public ArrayList<Reserva> getReservas() {
        return reservaRepositorio.getReservas();
    }

    public Reserva buscarReserva(String cedulaCliente) throws Exception {
        return reservaRepositorio.buscarReserva(cedulaCliente);
    }
}
