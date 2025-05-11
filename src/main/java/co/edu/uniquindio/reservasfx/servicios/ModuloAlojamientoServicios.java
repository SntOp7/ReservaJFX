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

    private EmpresaServicio empresaServicio = EmpresaServicio.getInstancia();
    private final HabitacionServicios habitacionServicios;
    private final AlojamientoServicios alojamientoServicios;

    public ModuloAlojamientoServicios() {
        habitacionServicios = new HabitacionServicios();
        alojamientoServicios = new AlojamientoServicios(habitacionServicios);
    }

    @Override
    public void registrarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception {
        alojamientoServicios.registrarAlojamiento(id, tipoAlojamiento, nombre, ciudad, descripcion, precioPorNoche, capacidadMaxima, servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento, habitaciones);
    }

    @Override
    public void editarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception{
        alojamientoServicios.editarAlojamiento(tipoAlojamiento, nombre, ciudad, descripcion, precioPorNoche, capacidadMaxima, servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento, habitaciones);
    }

    @Override
    public void eliminarAlojamiento(String nombre) throws Exception {
        alojamientoServicios.eliminarAlojamiento(nombre);
    }

    @Override
    public void obtenerAlojamientosPopulares(ArrayList<Alojamiento> alojamientos) {
        alojamientoServicios.obtenerAlojamientosPopulares(alojamientos);
    }

    @Override
    public void obtenerAlojamientosOferta(ArrayList<Alojamiento> alojamientos) {
        alojamientoServicios.obtenerAlojamientosOferta(alojamientos);
    }

    @Override
    public void obtenerAlojamientosPreferencias(ArrayList<Alojamiento> alojamientos) {
        alojamientoServicios.obtenerAlojamientosPreferencias(alojamientos);
    }

    @Override
    public void registrarHAbitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception {
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
