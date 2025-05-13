package co.edu.uniquindio.reservasfx.servicios.modulo.comercial;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Factura;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;
import co.edu.uniquindio.reservasfx.repositorios.ReservaRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.UsuarioServicios;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

public class ReservaServicios {

    private final ReservaRepositorio reservaRepositorio;
    private final UsuarioServicios usuarioServicios;
    private final AlojamientoServicios alojamientoServicios;

    public ReservaServicios(UsuarioServicios usuarioServicios, AlojamientoServicios alojamientoServicios) {
        reservaRepositorio = new ReservaRepositorio();
        this.usuarioServicios = usuarioServicios;
        this.alojamientoServicios = alojamientoServicios;
    }

    public void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                                int numeroHuespedes, ArrayList<Oferta> ofertasAlojamiento) throws Exception {

        if (fechaInicio == null) throw new Exception("La fecha de inicio es obligatoria");
        if (fechaFin == null) throw new Exception("La fecha de fin es obligatoria");
        if (fechaFin.isBefore(fechaInicio)) throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio");
        if (numeroHuespedes <= 0) throw new Exception("El número de huéspedes debe ser mayor a cero");

        Cliente cliente = usuarioServicios.buscarClientePorCedula(cedulaCliente);
        Alojamiento alojamiento = alojamientoServicios.buscarAlojamientoPorId(idAlojamiento);

        if (!tieneSaldoSuficiente(cliente, alojamiento, fechaInicio, fechaFin, ofertasAlojamiento))
            throw new Exception("El cliente no tiene saldo suficiente para realizar la reserva");
        if (!alojamientoTieneCapacidad(alojamiento, numeroHuespedes))
            throw new Exception("El alojamiento no tiene capacidad suficiente para el número de huéspedes");
        if (!alojamientoDisponible(alojamiento, fechaInicio, fechaFin))
            throw new Exception("El alojamiento no está disponible en las fechas seleccionadas");
        if (!ofertaEsValida(ofertasAlojamiento, fechaInicio, fechaFin))
            throw new Exception("La oferta no es válida para las fechas seleccionadas");
    }

    private boolean tieneSaldoSuficiente(Cliente cliente, Alojamiento alojamiento, LocalDate inicio, LocalDate fin,
                                          ArrayList<Oferta> ofertas) {
        double precioBase = alojamiento.getPrecioPorNoche() * ChronoUnit.DAYS.between(inicio, fin);
        double descuento = calcularDescuento(ofertas, inicio, fin);
        double total = precioBase - (precioBase * descuento);
        return cliente.getBilletera().getSaldo() >= total;
    }

    private double calcularDescuento(ArrayList<Oferta> ofertas, LocalDate inicio, LocalDate fin) {
        for (Oferta oferta : ofertas) {
            if ((oferta.getFechaInicio().isEqual(inicio) || oferta.getFechaInicio().isBefore(inicio)) &&
                    (oferta.getFechaFin().isEqual(fin) || oferta.getFechaFin().isAfter(fin))) {
                return oferta.getPorcentajeDescuento();
            }
        }
        return 0;
    }

    private boolean alojamientoTieneCapacidad(Alojamiento alojamiento, int huespedes) {
        return alojamiento.getCapacidadMaxima() >= huespedes;
    }

    private boolean alojamientoDisponible(Alojamiento alojamiento, LocalDate inicio, LocalDate fin) {
        return true;
    }

    private boolean ofertaEsValida(ArrayList<Oferta> ofertas, LocalDate inicio, LocalDate fin) {
        for (Oferta oferta : ofertas) {
            if ((oferta.getFechaInicio().isEqual(inicio) || oferta.getFechaInicio().isBefore(inicio)) &&
                    (oferta.getFechaFin().isEqual(fin) || oferta.getFechaFin().isAfter(fin))) {
                return true;
            }
        }
        return false;
    }

    public void cancelarReserva(String id) throws Exception {
        Reserva reserva = reservaRepositorio.buscarReservaPorId(id);
        if (reserva == null) throw new Exception("La reserva no existe");
        reservaRepositorio.cancelar(reserva);
    }

    public ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) {
        return reservaRepositorio.obtenerReservasCliente(cedulaCliente);
    }

    public EstadisticasAlojamiento obtenerEstadisticasAlojamiento(String idAlojamiento) {
    }

    public EstadisticasTipoAlojamiento obtenerRentabilidadTipoAlojamiento(int mes) {
    }
}
