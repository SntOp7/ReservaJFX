package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IComercial {

    void registrarOferta(OfertaEspecial ofertaEspecial, String descripcion, LocalDate fechaInicio,
                         LocalDate fechaFin, double porcentajeDescuento) throws Exception;

    void editarOferta(OfertaEspecial ofertaEspecial, String nombre, String descripcion, LocalDate fechaInicio,
                      LocalDate fechaFin, double porcentajeDescuento);

    void eliminarOferta(String nombre) throws Exception;

    void registrarReserva(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                          int numeroHuespedes) throws Exception;

    void cancelarReserva(String cedulaCliente) throws Exception;

    ArrayList<Reserva> getReservas();
}
