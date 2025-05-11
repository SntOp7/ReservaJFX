package co.edu.uniquindio.reservasfx.servicios;


import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IEmpresa;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmpresaServicio implements IEmpresa {

    public static EmpresaServicio INSTANCIA;

    private final ModuloUsuarioServicios moduloUsuarioServicios;
    private final ModuloComercialServicios moduloComercialServicios;
    private final ModuloAlojamientoServicios moduloAlojamientoServicios;

    private EmpresaServicio() {
        moduloUsuarioServicios = new ModuloUsuarioServicios();
        moduloComercialServicios = new ModuloComercialServicios();
        moduloAlojamientoServicios = new ModuloAlojamientoServicios();
    }

    public static EmpresaServicio getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new EmpresaServicio();
        }
        return INSTANCIA;
    }

    @Override
    public void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email,
                                 String contrasenia, boolean activacion) throws Exception {
        moduloUsuarioServicios.registrarCliente(cedula, nombre, telefono, direccion, email, contrasenia, activacion);
    }

    @Override
    public void editarCliente(String cedula, String nombre, String telefono, String direccion, String email) throws Exception {
        moduloUsuarioServicios.editarCliente(cedula, nombre, telefono, direccion, email);
    }

    @Override
    public void eliminarCliente(String cedula) throws Exception {
        moduloUsuarioServicios.eliminarCliente(cedula);
    }

    @Override
    public void registrarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                                     double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                                     String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento,
                                     ArrayList<Habitacion> habitaciones) throws Exception {
        moduloAlojamientoServicios.registrarAlojamiento(tipoAlojamiento, nombre, ciudad, descripcion, precioPorNoche,
                capacidadMaxima, servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento, habitaciones);
    }

    @Override
    public void editarAlojamiento(String nombre, String descripcion, double precioPorNoche, int capacidadMaxima,
                                  ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes,
                                  double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception {
        moduloAlojamientoServicios.editarAlojamiento(nombre, descripcion, precioPorNoche, capacidadMaxima, servicios,
                imagenPrincipal, imagenes, costoAseoYMantenimiento, habitaciones);
    }

    @Override
    public void eliminarAlojamiento(String id) throws Exception {
        moduloAlojamientoServicios.eliminarAlojamiento(id);
    }

    @Override
    public void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                                int numeroHuespedes) throws Exception {
        moduloComercialServicios.registrarReserva(cedulaCliente, idAlojamiento, fechaInicio, fechaFin, numeroHuespedes);
    }

    @Override
    public void cancelarReserva(String id) throws Exception {
        moduloComercialServicios.cancelarReserva(id);
    }

    @Override
    public void registrarOferta(OfertaEspecial ofertaEspecial, String idAlojamiento, String nombre,
                                String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                                double porcentajeDescuento) throws Exception {
        moduloComercialServicios.registrarOferta(ofertaEspecial, idAlojamiento, nombre, descripcion, fechaInicio,
                fechaFin, porcentajeDescuento);
    }

    @Override
    public void editarOferta(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                             double porcentajeDescuento) {
        moduloComercialServicios.editarOferta(nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
    }

    @Override
    public void eliminarOferta(String id) throws Exception {
        moduloComercialServicios.eliminarOferta(id);
    }

    @Override
    public void enviarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception {
        moduloUsuarioServicios.enviarCalificacion(nombreCliente, nombreAlojamiento, comentario, valoracion);
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
