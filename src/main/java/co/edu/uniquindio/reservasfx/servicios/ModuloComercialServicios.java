package co.edu.uniquindio.reservasfx.servicios;

import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IComercial;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.OfertaServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.ReservaServicios;

import java.time.LocalDate;
import java.util.ArrayList;

public class ModuloComercialServicios implements IComercial {

    private final ReservaServicios reservaServicios;
    private final OfertaServicios ofertaServicios;

    public ModuloComercialServicios() {
        reservaServicios = new ReservaServicios();
        ofertaServicios = new OfertaServicios();
    }

    @Override
    public void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                                int numeroHuespedes) throws Exception {
        reservaServicios.realizarReserva(cedulaCliente, idAlojamiento, fechaInicio, fechaFin, numeroHuespedes);
    }

    @Override
    public void cancelarReserva(String id) throws Exception {
        reservaServicios.cancelarReserva(id);
    }

    @Override
    public ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) throws Exception {
        return reservaServicios.obtenerReservasCliente(cedulaCliente);
    }

    @Override
    public void registrarOferta(OfertaEspecial ofertaEspecial, String idAlojamiento, String nombre, String descripcion,
                                LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) throws Exception {
        ofertaServicios.registrarOferta(ofertaEspecial, idAlojamiento, nombre, descripcion, fechaInicio, fechaFin,
                porcentajeDescuento);
    }

    @Override
    public void editarOferta(String id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                             double porcentajeDescuento) throws Exception {
        ofertaServicios.editarOferta(id, nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
    }

    @Override
    public void eliminarOferta(String id) throws Exception {
        ofertaServicios.eliminarOferta(id);
    }

    @Override
    public EstadisticasAlojamiento obtenerEstadisticasAlojamiento(String idAlojamiento) throws Exception {
        return reservaServicios.obtenerEstadisticasAlojamiento(idAlojamiento);
    }

    @Override
    public EstadisticasTipoAlojamiento obtenerRentabilidadTipoAlojamiento(int mes) throws Exception {
        return reservaServicios.obtenerRentabilidadTipoAlojamiento(mes);
    }
}
