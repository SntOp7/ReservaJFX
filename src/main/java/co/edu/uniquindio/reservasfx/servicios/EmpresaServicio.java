package co.edu.uniquindio.reservasfx.servicios;


import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IEmpresa;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmpresaServicio implements IEmpresa {

    private final ModuloUsuarioServicios moduloUsuarioServicios;
    private final ModuloComercialServicios moduloComercialServicios;
    private final ModuloAlojamientoServicios moduloAlojamientoServicios;

    public EmpresaServicio() {
        moduloAlojamientoServicios = new ModuloAlojamientoServicios();
        moduloUsuarioServicios = new ModuloUsuarioServicios(this);
        moduloComercialServicios = new ModuloComercialServicios(this);
    }

    @Override
    public void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email,
                                 String contrasenia, boolean activacion) throws Exception {
        moduloUsuarioServicios.registrarCliente(cedula, nombre, telefono, direccion, email, contrasenia, activacion);
    }

    @Override
    public void editarCliente(Cliente antiguo, String cedula, String nombre, String telefono, String direccion, String email) throws Exception {
        moduloUsuarioServicios.editarCliente(antiguo, cedula, nombre, telefono, direccion, email);
    }

    @Override
    public void eliminarCliente(String cedula) throws Exception {
        moduloUsuarioServicios.eliminarCliente(cedula);
    }

    @Override
    public void registrarAlojamiento(String tipoAlojamiento, String nombre, String ciudad, String descripcion,
                                     String precioPorNoche, String capacidadMaxima, ArrayList<TipoServicio> servicios,
                                     String imagenPrincipal, ArrayList<String> imagenes, String costoAseoYMantenimiento,
                                     ArrayList<Habitacion> habitaciones) throws Exception {
        moduloAlojamientoServicios.registrarAlojamiento(tipoAlojamiento, nombre, ciudad, descripcion, precioPorNoche,
                capacidadMaxima, servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento, habitaciones);
    }

    @Override
    public void editarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, String descripcion, String precioPorNoche, String capacidadMaxima,
                                  ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes,
                                  String costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception {
        moduloAlojamientoServicios.editarAlojamiento(id, tipoAlojamiento, nombre, descripcion, precioPorNoche, capacidadMaxima, servicios,
                imagenPrincipal, imagenes, costoAseoYMantenimiento, habitaciones);
    }

    @Override
    public void eliminarAlojamiento(String id) throws Exception {
        moduloAlojamientoServicios.eliminarAlojamiento(id);
    }

    @Override
    public void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                                String numeroHuespedes, String numeroHabitacion) throws Exception {
        moduloComercialServicios.realizarReserva(cedulaCliente, idAlojamiento, fechaInicio, fechaFin, numeroHuespedes,
                numeroHabitacion);
    }

    @Override
    public void cancelarReserva(String id) throws Exception {
        moduloComercialServicios.cancelarReserva(id);
    }

    @Override
    public void registrarOferta(OfertaEspecial ofertaEspecial, String idAlojamiento, String nombre,
                                String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                                String porcentajeDescuento) throws Exception {
        moduloComercialServicios.registrarOferta(ofertaEspecial, idAlojamiento, nombre, descripcion, fechaInicio,
                fechaFin, porcentajeDescuento);
    }

    @Override
    public void editarOferta(String id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                             String porcentajeDescuento) throws Exception {
        moduloComercialServicios.editarOferta(id, nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
    }

    @Override
    public void eliminarOferta(String id) throws Exception {
        moduloComercialServicios.eliminarOferta(id);
    }

    @Override
    public void enviarCalificacion(String cedulaCliente, String idAlojamiento, String comentario, String valoracion,
                                   ArrayList<Reserva> reservasCliente) throws Exception {
        moduloUsuarioServicios.enviarCalificacion(cedulaCliente, idAlojamiento, comentario, valoracion, reservasCliente);
    }

    @Override
    public void enviarNotificacion(String cedulaCliente, String titulo, String mensaje) throws Exception {
        moduloUsuarioServicios.enviarNotificacion(cedulaCliente, titulo, mensaje);
    }

    @Override
    public ModuloUsuarioServicios getModuloUsuarioServicios() {
        return moduloUsuarioServicios;
    }

    @Override
    public ModuloComercialServicios getModuloComercialServicios() {
        return moduloComercialServicios;
    }

    @Override
    public ModuloAlojamientoServicios getModuloAlojamientoServicios() {
        return moduloAlojamientoServicios;
    }
}
