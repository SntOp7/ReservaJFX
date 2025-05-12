package co.edu.uniquindio.reservasfx.servicios;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.Mes;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IAlojamiento;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.HabitacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.ReservaServicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

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
    public void editarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, String descripcion,
                                  double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                                  String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento) throws Exception {
        alojamientoServicios.editarAlojamiento(id, tipoAlojamiento, nombre, descripcion, precioPorNoche, capacidadMaxima, servicios,
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
    public ArrayList<Alojamiento> obtenerAlojamientosPopulares(Ciudad ciudad, LinkedList<Reserva> reservas) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPopulares(ciudad, reservas);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosOfertados(LinkedList<Oferta> ofertas) throws Exception {
        return alojamientoServicios.obtenerAlojamientosOfertados(ofertas);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(LinkedList<Reserva> reservasCliente) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPreferenciasCliente(reservasCliente);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(LinkedList<Deseo> deseosCliente) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPorDeseosCliente(deseosCliente);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPorFiltro(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad,
                                                               double precioMin, double precioMax) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPorFiltro(tipoAlojamiento, nombre, ciudad, precioMin, precioMax);
    }

    @Override
    public void registrarHabitacion(String idHotel, int numero, double precio, int capacidad, String descripcion,
                                    String imagen) throws Exception {
        habitacionServicios.registrarHabitacion(idHotel, numero, precio, capacidad, descripcion, imagen);
    }

    @Override
    public void editarHabitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception {
        habitacionServicios.editarHabitacion(idHotel, numero, precio, capacidad, descripcion, imagen);
    }

    @Override
    public void eliminarHabitacion(String idHotel, int numero) throws Exception {
        habitacionServicios.eliminarHabitacion(idHotel, numero);
    }
}
