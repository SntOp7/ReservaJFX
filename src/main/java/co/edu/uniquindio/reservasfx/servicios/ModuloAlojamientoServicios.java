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
import co.edu.uniquindio.reservasfx.modelo.factory.Hotel;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IAlojamiento;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.HabitacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.ReservaServicios;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

@Getter
public class ModuloAlojamientoServicios implements IAlojamiento {

    private final AlojamientoServicios alojamientoServicios;
    private final HabitacionServicios habitacionServicios;

    public ModuloAlojamientoServicios() {
        habitacionServicios = new HabitacionServicios();
        alojamientoServicios = new AlojamientoServicios();
    }

    @Override
    public void registrarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                                     double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                                     String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento,
                                     ArrayList<Habitacion> habitaciones) throws Exception {
        Alojamiento alojamiento = alojamientoServicios.registrarAlojamiento(tipoAlojamiento, nombre, ciudad, descripcion,
                precioPorNoche, capacidadMaxima, servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento);
        if (alojamiento instanceof Hotel) {
            if (habitaciones == null || habitaciones.isEmpty())
                throw new Exception("Debe registrar al menos una habitación para el Hotel");
            for (Habitacion habitacion : habitaciones) {
                habitacionServicios.registrarHabitacion(alojamiento.getId(), habitacion.getNumero(), habitacion.getPrecio(),
                        habitacion.getCapacidad(), habitacion.getDescripcion(), habitacion.getImagen());
            }
        }
    }

    @Override
    public void editarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, String descripcion,
                                  double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                                  String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento,
                                  ArrayList<Habitacion> habitaciones) throws Exception {
        Alojamiento alojamiento = alojamientoServicios.editarAlojamiento(id, tipoAlojamiento, nombre, descripcion,
                precioPorNoche, capacidadMaxima, servicios, imagenPrincipal, imagenes, costoAseoYMantenimiento);
        if (alojamiento instanceof Hotel) {
            if (habitaciones == null || habitaciones.isEmpty())
                throw new Exception("Debe quedar registrado al menos una Habitación para el Hotel");
            for (Habitacion habitacion : habitaciones) {
                habitacionServicios.editarHabitacion(alojamiento.getId(), habitacion.getNumero(), habitacion.getPrecio(),
                        habitacion.getCapacidad(), habitacion.getDescripcion(), habitacion.getImagen());
            }
        }
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
    public ArrayList<Alojamiento> obtenerAlojamientosPopulares(Ciudad ciudad, ArrayList<Reserva> reservas) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPopulares(ciudad, reservas);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosOfertados(ArrayList<Oferta> ofertas) throws Exception {
        return alojamientoServicios.obtenerAlojamientosOfertados(ofertas);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(ArrayList<Reserva> reservasCliente) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPreferenciasCliente(reservasCliente);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(ArrayList<Deseo> deseosCliente) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPorDeseosCliente(deseosCliente);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosPorFiltro(TipoAlojamiento tipoAlojamiento, String nombre, String ciudad,
                                                               String precioMin, String precioMax) throws Exception {
        return alojamientoServicios.obtenerAlojamientosPorFiltro(tipoAlojamiento, nombre, ciudad, precioMin, precioMax);
    }

    @Override
    public ArrayList<Alojamiento> obtenerAlojamientosRango(int min, int max, ArrayList<Alojamiento> totalAlojamientos) throws Exception {
        ArrayList<Alojamiento> alojamientos = new ArrayList<>();
        int limiteSuperior = Math.min(max, totalAlojamientos.size() - 1);
        for (int i = min; i <= limiteSuperior; i++) {
            alojamientos.add(totalAlojamientos.get(i));
        }
        return alojamientos;
    }

    @Override
    public ArrayList<Servicio> obtenerServiciosAlojamiento(String idAlojamiento) throws Exception {
        return alojamientoServicios.obtenerServiciosAlojamiento(idAlojamiento);
    }

    @Override
    public ArrayList<Imagen> obtenerImagenesAlojamiento(String idAlojamiento) throws Exception {
        return alojamientoServicios.obtenerImagenesAlojamiento(idAlojamiento);
    }


    @Override
    public ArrayList<Habitacion> obtenerHabitacionesHotel(String idAlojamiento) throws Exception {
        return habitacionServicios.obtenerHabitacionesHotel(idAlojamiento);
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
