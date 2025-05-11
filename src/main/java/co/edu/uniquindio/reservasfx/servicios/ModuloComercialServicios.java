package co.edu.uniquindio.reservasfx.servicios;

import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
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
    public void registrarOferta(OfertaEspecial ofertaEspecial, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) throws Exception {
        ofertaServicios.registrarOferta(ofertaEspecial, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
    }

    @Override
    public void editarOferta(OfertaEspecial ofertaEspecial, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) {
        ofertaServicios.editarOferta(ofertaEspecial, nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
    }

    @Override
    public void eliminarOferta(String nombre) throws Exception {
        ofertaServicios.eliminarOferta(nombre);
    }

    @Override
    public void registrarReserva(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, int numeroHuespedes) throws Exception {
        reservaServicios.registrarReserva(cliente, alojamiento, fechaInicio, fechaFin, numeroHuespedes);
    }

    @Override
    public void cancelarReserva(String cedulaCliente) throws Exception {
        reservaServicios.cancelarReserva(cedulaCliente);
    }

    @Override
    public ArrayList<Reserva> getReservas() {
        return reservaServicios.getReservas();
    }
}
