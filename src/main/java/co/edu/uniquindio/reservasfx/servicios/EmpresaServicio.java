package co.edu.uniquindio.reservasfx.servicios;


import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
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
        moduloUsuarioServicios = new ModuloUsuarioServicios();
        moduloComercialServicios = new ModuloComercialServicios();
        moduloAlojamientoServicios = new ModuloAlojamientoServicios();
    }

    @Override
    public void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia, boolean activacion) throws Exception {
        moduloUsuarioServicios.registrarCliente(cedula, nombre, telefono, direccion, email, contrasenia, activacion);
    }

    @Override
    public void editarCliente(String cedula, String nombre, String telefono, String email, String contrasenia) throws Exception {
        moduloUsuarioServicios.editarCliente(cedula, nombre, telefono, email, contrasenia);
    }

    @Override
    public void eliminarCliente(String cedula) throws Exception {
        moduloUsuarioServicios.eliminarCliente(cedula);
    }

    @Override
    public void registrarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception {
        moduloAlojamientoServicios.registrarAlojamiento(id, tipoAlojamiento, nombre, ciudad, descripcion, precioPorNoche, capacidadMaxima, servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento, habitaciones);
    }

    @Override
    public void editarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception {
        moduloAlojamientoServicios.editarAlojamiento(tipoAlojamiento, nombre, ciudad, descripcion, precioPorNoche, capacidadMaxima, servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento, habitaciones);
    }

    @Override
    public void eliminarAlojamiento(String nombre) throws Exception {
        moduloAlojamientoServicios.eliminarAlojamiento(nombre);
    }

    @Override
    public void registrarReserva(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, int numeroHuespedes) throws Exception {
        moduloComercialServicios.registrarReserva(cliente, alojamiento, fechaInicio, fechaFin, numeroHuespedes);
    }

    @Override
    public void cancelarReserva(String cedulaCliente) throws Exception {
        moduloComercialServicios.cancelarReserva(cedulaCliente);
    }

    @Override
    public void registrarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception {
        moduloUsuarioServicios.registrarCalificacion(nombreCliente, nombreAlojamiento, comentario, valoracion);
    }

    @Override
    public void registrarOferta(OfertaEspecial ofertaEspecial, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) throws Exception {
        moduloComercialServicios.registrarOferta(ofertaEspecial, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
    }

    @Override
    public void editarOferta(OfertaEspecial ofertaEspecial, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) {
        moduloComercialServicios.editarOferta(ofertaEspecial, nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
    }

    @Override
    public void eliminarOferta(String nombre) throws Exception {
        moduloComercialServicios.eliminarOferta(nombre);
    }

    @Override
    public Usuario iniciarSesion(String email, String contrasenia) throws Exception {
        return moduloUsuarioServicios.iniciarSesion(email, contrasenia);
    }
}
