package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.servicios.ModuloAlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.ModuloComercialServicios;
import co.edu.uniquindio.reservasfx.servicios.ModuloUsuarioServicios;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IEmpresa {

    void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia,
                          boolean activacion) throws Exception;

    void editarCliente(String cedula, String nombre, String telefono, String direcccion, String email) throws Exception;

    void eliminarCliente(String cedula) throws Exception;

    void registrarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                              double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                              ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void editarAlojamiento(String nombre, String descripcion, double precioPorNoche, int capacidadMaxima,
                           ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes,
                           double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void eliminarAlojamiento(String id) throws Exception;

    void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                          int numeroHuespedes) throws Exception;

    void cancelarReserva(String id) throws Exception;

    void registrarOferta(OfertaEspecial ofertaEspecial, String idAlojamiento, String nombre, String descripcion,
                         LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) throws Exception;

    void editarOferta(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                      double porcentajeDescuento) throws Exception;

    void eliminarOferta(String id) throws Exception;

    void enviarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception;

    void enviarNotificacion(String cedulaCliente, String titulo, String mensaje) throws Exception;

    ModuloUsuarioServicios getModuloUsuarioServicios();

    ModuloComercialServicios getModuloComercialServicios();

    ModuloAlojamientoServicios getModuloAlojamientoServicios();
}
