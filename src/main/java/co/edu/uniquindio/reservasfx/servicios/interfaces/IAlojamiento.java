package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.util.ArrayList;

public interface IAlojamiento {

    void registrarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                              double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                              ArrayList<String> imagenes, double costoAseoYMantenimiento) throws Exception;

    void editarAlojamiento(String nombre, String descripcion, double precioPorNoche, int capacidadMaxima,
                           ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes,
                           double costoAseoYMantenimiento) throws Exception;

    void eliminarAlojamiento(String id) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientos() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosAleatorios() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPopulares() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosRentables() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosOfertados() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(String cedulaCliente) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(String cedulaCliente) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPorFiltro(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad,
                                                        double precioMin, double precioMax) throws Exception;

    void registrarHabitacion(int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception;

    void editarHabitacion(double precio, String descripcion, String imagen) throws Exception;

    void eliminarHabitacion(String idHotel, int numero) throws Exception;
}
