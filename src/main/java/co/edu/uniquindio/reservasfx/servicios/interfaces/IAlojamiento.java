package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.Mes;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IAlojamiento {

    void registrarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                              double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                              ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void editarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, String descripcion,
                           double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                           ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void eliminarAlojamiento(String id) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientos() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosAleatorios() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPopulares(Ciudad ciudad, ArrayList<Reserva> reservas) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosOfertados(ArrayList<Oferta> ofertas) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(ArrayList<Reserva> reservasCliente) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(ArrayList<Deseo> deseosCliente) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPorFiltro(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad,
                                                        double precioMin, double precioMax) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosRango(int min, int max, ArrayList<Alojamiento> totalAlojamientos) throws Exception;

    ArrayList<Servicio> obtenerServiciosAlojamiento(String idAlojamiento) throws Exception;

    ArrayList<Imagen> obtenerImagenesAlojamiento(String idAlojamiento) throws Exception;

    ArrayList<Habitacion> obtenerHabitacionesHotel(String idAlojamiento) throws Exception;

    void registrarHabitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception;

    void editarHabitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception;

    void eliminarHabitacion(String idHotel, int numero) throws Exception;
}
