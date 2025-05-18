package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.servicios.ModuloAlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.ModuloComercialServicios;
import co.edu.uniquindio.reservasfx.servicios.ModuloUsuarioServicios;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IEmpresa {

    void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia,
                          boolean activacion) throws Exception;

    void editarCliente(Cliente antiguo, String cedula, String nombre, String telefono, String direcccion, String email) throws Exception;

    void eliminarCliente(String cedula) throws Exception;

    void registrarAlojamiento(String tipoAlojamiento, String nombre, String ciudad, String descripcion,
                              String precioPorNoche, String capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                              ArrayList<String> imagenes, String costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void editarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, String descripcion, String precioPorNoche,
                           String capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                           ArrayList<String> imagenes, String costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void eliminarAlojamiento(String id) throws Exception;

    void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                          String numeroHuespedes, String numeroHabitacion) throws Exception;

    void cancelarReserva(String id) throws Exception;

    void registrarOferta(OfertaEspecial ofertaEspecial, String idAlojamiento, String nombre, String descripcion,
                         LocalDate fechaInicio, LocalDate fechaFin, String porcentajeDescuento) throws Exception;

    void editarOferta(String id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                      String porcentajeDescuento) throws Exception;

    void eliminarOferta(String id) throws Exception;

    void enviarCalificacion(String cedulaCliente, String idAlojamiento, String comentario, String valoracion,
                            ArrayList<Reserva> reservasCliente) throws Exception;

    void enviarNotificacion(String cedulaCliente, String titulo, String mensaje) throws Exception;

    ModuloUsuarioServicios getModuloUsuarioServicios();

    ModuloComercialServicios getModuloComercialServicios();

    ModuloAlojamientoServicios getModuloAlojamientoServicios();
}
