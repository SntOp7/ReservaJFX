package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IEmpresa {

    void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia,
                          boolean activacion) throws Exception;

    void editarCliente(String cedula, String nombre, String telefono, String email, String contrasenia) throws Exception;

    void eliminarCliente(String cedula) throws Exception;

    void registrarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void editarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                           double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                           String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento,
                           ArrayList<Habitacion> habitaciones) throws Exception;

    void eliminarAlojamiento(String nombre) throws Exception;

    void registrarReserva(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                          int numeroHuespedes) throws Exception;

    void cancelarReserva(String cedulaCliente) throws Exception;

    void registrarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception;

    void registrarOferta(OfertaEspecial ofertaEspecial, String descripcion, LocalDate fechaInicio,
                         LocalDate fechaFin, double porcentajeDescuento) throws Exception;

    void editarOferta(OfertaEspecial ofertaEspecial, String nombre, String descripcion, LocalDate fechaInicio,
                      LocalDate fechaFin, double porcentajeDescuento) throws Exception;

    void eliminarOferta(String nombre) throws Exception;

    Usuario iniciarSesion(String email, String contrasenia) throws Exception;
}
