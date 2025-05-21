package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IComercial {

    void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                         String numeroHuespedes, int numeroHabitacion) throws Exception;

    void cancelarReserva(String id) throws Exception;

    ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) throws Exception;

    void registrarOferta(String ofertaEspecial, String idAlojamiento, String nombre, String descripcion,
                         LocalDate fechaInicio, LocalDate fechaFin, String porcentajeDescuento) throws Exception;

    void editarOferta(String id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                      String porcentajeDescuento) throws Exception;

    void eliminarOferta(String id) throws Exception;

    ArrayList<Oferta> obtenerOfertas() throws Exception;

    ArrayList<Oferta> obtenerOfertasAlojamiento(String idAlojamiento) throws Exception;

    ArrayList<Oferta> obtenerOfertasActivasAlojamiento(String idAlojamiento) throws Exception;

    void actualizarEstadoReservas() throws Exception;

    void actualizarEstadoOfertas() throws Exception;

    EstadisticasAlojamiento obtenerEstadisticasAlojamiento(String idAlojamiento) throws Exception;

    ArrayList<EstadisticasTipoAlojamiento> obtenerRentabilidadTipoAlojamiento(int mes) throws Exception;
}
