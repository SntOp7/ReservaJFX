package co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.AlojamientoFactory;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.repositorios.AlojamientoRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.ImagenRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.ServicioRepositorio;
import co.edu.uniquindio.reservasfx.servicios.ModuloAlojamientoServicios;

import java.util.ArrayList;
import java.util.Comparator;

public class AlojamientoServicios {

    private final AlojamientoRepositorio alojamientoRepositorio;
    private final ServicioRepositorio servicioRepositorio;
    private final ImagenRepositorio imagenRepositorio;
    private final HabitacionServicios habitacionServicios;
    private ArrayList<Alojamiento> alojamientos;


    public AlojamientoServicios(HabitacionServicios habitacionServicios) {
        this.habitacionServicios = habitacionServicios;
        this.servicioRepositorio = new ServicioRepositorio();
        this.imagenRepositorio = new ImagenRepositorio();
        alojamientoRepositorio = new AlojamientoRepositorio();
    }

    public void registrarAlojamiento(String id,TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception{
        if (id == null || id.isEmpty()) throw new Exception("EL id es obligatorio");
        if (tipoAlojamiento == null) throw new Exception("El tipo de alojamiento es obligatorio");
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (ciudad == null) throw new Exception("La ciudad es obligatoria");
        if (descripcion == null || descripcion.isEmpty()) throw new Exception("La descripción es obligatoria");
        if (precioPorNoche <= 0) throw new Exception("El precio por noche debe ser mayor a 0");
        if (capacidadMaxima <= 0) throw new Exception("La capacidad máxima debe ser mayor a 0");
        if (imagenPrincipal == null || imagenPrincipal.isEmpty()) throw new Exception("La imagen principal es obligatoria");

        Alojamiento alojamiento;

        switch (tipoAlojamiento) {
            case CASA:
                alojamiento = AlojamientoFactory.crearCasa(id, nombre, ciudad, descripcion, precioPorNoche,
                        capacidadMaxima, imagenPrincipal, costoAseoYMantenimiento);
                break;
            case APARTAMENTO:
                alojamiento = AlojamientoFactory.crearApartamento(id, nombre, ciudad, descripcion, precioPorNoche,
                        capacidadMaxima, imagenPrincipal, costoAseoYMantenimiento);
                break;
            case HOTEL:
                alojamiento = AlojamientoFactory.crearHotel(id, nombre, ciudad, descripcion, precioPorNoche,
                        capacidadMaxima, imagenPrincipal);
                break;
            default:
                throw new Exception("Tipo de alojamiento no soportado");
        }

        alojamientoRepositorio.agregar(alojamiento);

    }


    public void editarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento, ArrayList<Habitacion> habitaciones) throws Exception{
        Alojamiento existente = buscarAlojamiento(nombre);
        if (existente == null) throw new Exception("El alojamiento no existe");

        String id = existente.getId();
        alojamientos.remove(existente);

        Alojamiento actualizado;

        switch (tipoAlojamiento) {
            case CASA:
                actualizado = AlojamientoFactory.crearCasa(id, nombre, ciudad, descripcion, precioPorNoche,
                        capacidadMaxima, imagenPrincipal, costoAseoYMantenimiento);
                break;
            case APARTAMENTO:
                actualizado = AlojamientoFactory.crearApartamento(id, nombre, ciudad, descripcion, precioPorNoche,
                        capacidadMaxima, imagenPrincipal, costoAseoYMantenimiento);
                break;
            case HOTEL:
                actualizado = AlojamientoFactory.crearHotel(id, nombre, ciudad, descripcion, precioPorNoche,
                        capacidadMaxima, imagenPrincipal);
                break;
            default:
                throw new Exception("Tipo de alojamiento no soportado");
        }

       alojamientoRepositorio.editar(actualizado);

    }

    public void eliminarAlojamiento(String nombre) throws Exception {
        Alojamiento alojamiento = buscarAlojamiento(nombre);
        if (alojamiento == null) throw new Exception("El alojamiento no existe");
        alojamientoRepositorio.eliminar(alojamiento);
    }

    public void obtenerAlojamientosPopulares(ArrayList<Alojamiento> alojamientos) {
        alojamientos.stream()
                .sorted(Comparator.comparingInt(Alojamiento::getVisitas).reversed())
                .limit(5)
                .forEach(alojamientos::add);
    }

    public void obtenerAlojamientosOferta(ArrayList<Alojamiento> alojamientos) {
        double umbral = 100000; // ejemplo: 100.000 por noche
        for (Alojamiento a : alojamientos) {
            if (a.getPrecioPorNoche() < umbral) {
                alojamientos.add(a);
            }
        }
    }

    public void obtenerAlojamientosPreferencias(ArrayList<Alojamiento> alojamientos) {
        Ciudad ciudadPreferida = Ciudad.MEDELLIN;
        for (Alojamiento a : alojamientos) {
            if (a.getCiudad() == ciudadPreferida && a instanceof Casa casa && casa.getCapacidadMaxima() > 4) {
                alojamientos.add(a);
            }
        }
    }

    public Alojamiento buscarAlojamiento(String nombre) {
        return alojamientoRepositorio.buscarAlojamiento(nombre);
    }
}
