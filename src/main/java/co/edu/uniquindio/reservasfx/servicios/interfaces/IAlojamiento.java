package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.util.ArrayList;

public interface IAlojamiento {

    void registrarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, String ciudad, String descripcion,
                              String precioPorNoche, String capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                              ArrayList<String> imagenes, String costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void editarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, String descripcion,
                           String precioPorNoche, String capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                           ArrayList<String> imagenes, String costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception;

    void eliminarAlojamiento(String id) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientos() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosAleatorios() throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPopulares(Ciudad ciudad, ArrayList<Reserva> reservas) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosOfertados(ArrayList<Oferta> ofertas) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(ArrayList<Reserva> reservasCliente) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(ArrayList<Deseo> deseosCliente) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosPorFiltro(TipoAlojamiento tipoAlojamiento, String nombre, String ciudad,
                                                        String precioMin, String precioMax) throws Exception;

    ArrayList<Alojamiento> obtenerAlojamientosRango(int min, int max, ArrayList<Alojamiento> totalAlojamientos) throws Exception;

    ArrayList<Servicio> obtenerServiciosAlojamiento(String idAlojamiento) throws Exception;

    ArrayList<Imagen> obtenerImagenesAlojamiento(String idAlojamiento) throws Exception;

    ArrayList<Habitacion> obtenerHabitacionesHotel(String idAlojamiento) throws Exception;

    Habitacion crearHabitacion(String numero, String precio, String capacidad, String descripcion, String imagen) throws Exception;

    void registrarHabitacion(String idHotel, Habitacion habitacion) throws Exception;

    Habitacion verificarEdicionHabitacion(Habitacion habitacionAntigua, String numero, String precio,
                                    String capacidad, String descripcion, String imagen, ArrayList<Habitacion> habitaciones) throws Exception;

    void editarHabitacion(String idHotel, Habitacion habitacion) throws Exception;

    void eliminarHabitacion(String idHotel, int numero) throws Exception;
}
