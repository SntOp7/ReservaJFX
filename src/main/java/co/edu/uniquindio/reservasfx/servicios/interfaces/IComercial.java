package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IComercial {

    void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                         int numeroHuespedes) throws Exception;

    void cancelarReserva(String id) throws Exception;

    ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) throws Exception;

    void registrarOferta(OfertaEspecial ofertaEspecial, String idAlojamiento, String nombre, String descripcion,
                         LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) throws Exception;

    void editarOferta(String id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                      double porcentajeDescuento) throws Exception;

    void eliminarOferta(String id) throws Exception;

    EstadisticasAlojamiento obtenerEstadisticasAlojamiento(String idAlojamiento) throws Exception;

    EstadisticasTipoAlojamiento obtenerRentabilidadTipoAlojamiento(int mes) throws Exception;
}
