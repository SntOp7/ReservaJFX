package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.util.ArrayList;

public interface IAlojamiento {

    void registrarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                              double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                              String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento,
                              ArrayList<Habitacion> habitaciones) throws Exception;

    void editarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                           double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                           String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento,
                           ArrayList<Habitacion> habitaciones) throws Exception;

    void eliminarAlojamiento(String nombre) throws Exception;

    void obtenerAlojamientosPopulares(ArrayList<Alojamiento> alojamientos);

    void obtenerAlojamientosOferta(ArrayList<Alojamiento> alojamientos);

    void obtenerAlojamientosPreferencias(ArrayList<Alojamiento> alojamientos);

    void registrarHAbitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception;

    void editarHabitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception;

    void eliminarHabitacion(String idHotel, int numero) throws Exception;
}
