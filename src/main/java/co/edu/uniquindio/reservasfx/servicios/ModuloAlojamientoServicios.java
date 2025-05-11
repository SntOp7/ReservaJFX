package co.edu.uniquindio.reservasfx.servicios;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IAlojamiento;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.HabitacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.ReservaServicios;

import java.time.LocalDate;
import java.util.ArrayList;

public class ModuloAlojamientoServicios implements IAlojamiento {

    private final HabitacionServicios habitacionServicios;
    private final AlojamientoServicios alojamientoServicios;

    public ModuloAlojamientoServicios() {
        habitacionServicios = new HabitacionServicios();
        alojamientoServicios = new AlojamientoServicios();
    }

    @Override
    public void registrarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                                     double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                                     String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento) throws Exception {
        alojamientoServicios.registrarAlojamiento(tipoAlojamiento, nombre, ciudad, descripcion, precioPorNoche, capacidadMaxima,
                servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento);
    }

    @Override
    public void editarAlojamiento(String nombre, String descripcion, double precioPorNoche, int capacidadMaxima,
                                  ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes,
                                  double costoAseoYMantenimiento) throws Exception {
        alojamientoServicios.editarAlojamiento(nombre, descripcion, precioPorNoche, capacidadMaxima, servicios,
                imagenPrincipal, imagenes, costoAseoYMantenimiento);
    }

    @Override
    public void eliminarAlojamiento(String id) throws Exception {
        alojamientoServicios.eliminarAlojamiento(id);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientos() throws Exception {
        return alojamientoServicios.obtenerAlojamientos();
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosAleatorios() throws Exception {
        return alojamientoServicios.obtenerAlojamientosAleatorios();
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPopulares() throws Exception {
        return alojamientoServicios.obtenerAlojamientosPopulares();
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosRentables() throws Exception {
        return alojamientoServicios.obtenerAlojamientosRentables();
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosOfertados() throws Exception {
        return alojamientoServicios.obtenerAlojamientosOfertados();
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(String cedulaCliente) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPreferenciasCliente(cedulaCliente);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(String cedulaCliente) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPorDeseosCliente(cedulaCliente);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPorFiltro(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, double precioMin, double precioMax) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPorFiltro(tipoAlojamiento, nombre, ciudad, precioMin, precioMax);
    }

    @Override
    public void registrarHabitacion(int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception {

    }

    @Override
    public void editarHabitacion(double precio, String descripcion, String imagen) throws Exception {

    }

    @Override
    public void eliminarHabitacion(String idHotel, int numero) throws Exception {

    }
}
