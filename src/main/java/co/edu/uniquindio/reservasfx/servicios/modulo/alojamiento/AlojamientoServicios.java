package co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento;

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
import co.edu.uniquindio.reservasfx.modelo.factory.AlojamientoFactory;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.repositorios.AlojamientoRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.ImagenRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.ServicioRepositorio;

import java.util.*;

public class AlojamientoServicios {

    private final AlojamientoRepositorio alojamientoRepositorio;
    private final ServicioRepositorio servicioRepositorio;
    private final ImagenRepositorio imagenRepositorio;


    public AlojamientoServicios() {
        servicioRepositorio = new ServicioRepositorio();
        imagenRepositorio = new ImagenRepositorio();
        alojamientoRepositorio = new AlojamientoRepositorio();
    }

    public Alojamiento registrarAlojamiento(TipoAlojamiento tipoAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                                     double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                                     String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento) throws Exception {

        if (ciudad == null) throw new Exception("La ciudad es obligatoria");
        verificarCampos(tipoAlojamiento, nombre, descripcion, precioPorNoche, capacidadMaxima, servicios, imagenPrincipal,
                costoAseoYMantenimiento);

        String id = UUID.randomUUID().toString();
        Alojamiento alojamiento = switch (tipoAlojamiento) {
            case CASA -> AlojamientoFactory.crearCasa(id, nombre, ciudad, descripcion, precioPorNoche,
                    capacidadMaxima, imagenPrincipal, costoAseoYMantenimiento);
            case APARTAMENTO -> AlojamientoFactory.crearApartamento(id, nombre, ciudad, descripcion, precioPorNoche,
                    capacidadMaxima, imagenPrincipal, costoAseoYMantenimiento);
            case HOTEL -> AlojamientoFactory.crearHotel(id, nombre, ciudad, descripcion, precioPorNoche,
                    capacidadMaxima, imagenPrincipal);
        };
        registrarServicios(id, servicios);
        registrarImagenes(id, imagenes);
        alojamientoRepositorio.agregar(alojamiento);
        return alojamiento;
    }

    public void registrarServicios(String idAlojamiento, ArrayList<TipoServicio> servicios) {
        for (TipoServicio tipoServicio : servicios) {
            Servicio servicio = new Servicio(idAlojamiento, tipoServicio);
            servicioRepositorio.agregar(servicio);
        }
    }

    public void registrarImagenes(String idAlojamiento, ArrayList<String> imagenes) {
        if (imagenes == null) return;
        for (String imagen : imagenes) {
            Imagen imagenAlojamiento = new Imagen(idAlojamiento, imagen);
            imagenRepositorio.agregar(imagenAlojamiento);
        }
    }

    public Alojamiento editarAlojamiento(String id, TipoAlojamiento tipoAlojamiento, String nombre, String descripcion,
                                  double precioPorNoche, int capacidadMaxima, ArrayList<TipoServicio> servicios,
                                  String imagenPrincipal, ArrayList<String> imagenes, double costoAseoYMantenimiento) throws Exception {

        verificarCampos(tipoAlojamiento, nombre, descripcion, precioPorNoche, capacidadMaxima, servicios, imagenPrincipal,
                costoAseoYMantenimiento);
        Alojamiento alojamiento = alojamientoRepositorio.buscarAlojamientoPorId(id);
        alojamiento.setNombre(nombre);
        alojamiento.setDescripcion(descripcion);
        alojamiento.setPrecioPorNoche(precioPorNoche);
        alojamiento.setCapacidadMaxima(capacidadMaxima);
        alojamiento.setImagenPrincipal(imagenPrincipal);
        editarServicios(alojamiento.getId(), servicios);
        editarImagenes(alojamiento.getId(), imagenes);
        if (tipoAlojamiento.equals(TipoAlojamiento.CASA)) {
            ((Casa) alojamiento).setCostoAseoYMantenimiento(costoAseoYMantenimiento);
        } else if (tipoAlojamiento.equals((TipoAlojamiento.APARTAMENTO))) {
            ((Apartamento) alojamiento).setCostoAseoYMantenimiento(costoAseoYMantenimiento);
        }
        alojamientoRepositorio.editar(alojamiento);
        return alojamiento;
    }

    public void editarServicios(String idAlojamiento, ArrayList<TipoServicio> servicios) {
        eliminarServicios(idAlojamiento);
        for (TipoServicio tipoServicio : servicios) {
            Servicio servicio = new Servicio(idAlojamiento, tipoServicio);
            servicioRepositorio.agregar(servicio);
        }
    }

    public void editarImagenes(String idAlojamiento, ArrayList<String> imagenes) {
        if (imagenes == null) return;
        eliminarImagenes(idAlojamiento);
        for (String imagen : imagenes) {
            Imagen imagenAlojamiento = new Imagen(idAlojamiento, imagen);
            imagenRepositorio.agregar(imagenAlojamiento);
        }
    }

    public void verificarCampos(TipoAlojamiento tipoAlojamiento, String nombre, String descripcion, double precioPorNoche,
                                 int capacidadMaxima, ArrayList<TipoServicio> servicios, String imagenPrincipal,
                                 double costoAseoYMantenimiento) throws Exception {

        if (tipoAlojamiento == null) throw new Exception("El tipo de alojamiento es obligatorio");
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (descripcion == null || descripcion.isEmpty()) throw new Exception("La descripción es obligatoria");
        if (precioPorNoche <= 0) throw new Exception("El precio por noche debe ser mayor a 0");
        if (capacidadMaxima <= 0) throw new Exception("La capacidad máxima debe ser mayor a 0");
        if (imagenPrincipal == null || imagenPrincipal.isEmpty()) throw new Exception("La imagen principal es obligatoria");
        if (servicios == null || servicios.isEmpty()) throw new Exception("Debe tener al menos un servicio");

        if (tipoAlojamiento.equals(TipoAlojamiento.CASA) || tipoAlojamiento.equals(TipoAlojamiento.APARTAMENTO)) {
            if (costoAseoYMantenimiento <= 0) throw new Exception("El costo de aseo y mantenimiento debe ser mayor a 0");
        }
    }

    public void eliminarAlojamiento(String nombre) throws Exception {
        Alojamiento alojamiento = buscarAlojamiento(nombre);
        if (alojamiento == null) throw new Exception("El alojamiento no existe");
        alojamientoRepositorio.eliminar(alojamiento);
        eliminarServicios(alojamiento.getId());
        eliminarImagenes(alojamiento.getId());
    }

    public void eliminarServicios(String idAlojamiento) {
        servicioRepositorio.eliminarServiciosAlojamiento(idAlojamiento);
    }

    public void eliminarImagenes(String idAlojamiento) {
        imagenRepositorio.eliminarImagenesAlojamiento(idAlojamiento);
    }

    public Alojamiento buscarAlojamiento(String nombre) {
        return alojamientoRepositorio.buscarAlojamientoPorNombre(nombre);

    }

    public Alojamiento buscarAlojamientoPorId(String id) {
        return alojamientoRepositorio.buscarAlojamientoPorId(id);
    }

    public ArrayList<Alojamiento> obtenerAlojamientos() throws Exception {
        ArrayList<Alojamiento> alojamientos = alojamientoRepositorio.getAlojamientos();
        if (validarListaVacia(alojamientos)) throw new Exception("No hay alojamientos registrados de momento");
        return alojamientos;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosAleatorios() throws Exception {
        ArrayList<Alojamiento> alojamientos = alojamientoRepositorio.obtenerAlojamientosAleatorios();
        if (validarListaVacia(alojamientos)) throw new Exception("No hay alojamientos que mostrar de momento");
        return alojamientos;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosPopulares(Ciudad ciudad, ArrayList<Reserva> reservas) throws Exception {
        ArrayList<Alojamiento> alojamientos = alojamientoRepositorio.obtenerAlojamientosPopulares(ciudad, reservas);
        if (validarListaVacia(alojamientos)) throw new Exception("No hay alojamientos populares de momento");
        return alojamientos;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosOfertados(ArrayList<Oferta> ofertas) throws Exception {
        ArrayList<Alojamiento> alojamientos = alojamientoRepositorio.obtenerAlojamientosOfertados(ofertas);
        if (validarListaVacia(alojamientos)) throw new Exception("No hay alojamientos ofertados de momento");
        return alojamientos;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(ArrayList<Reserva> reservasCliente) throws Exception {
        ArrayList<Alojamiento> alojamientos = alojamientoRepositorio.obtenerAlojamientosPreferenciasCliente(reservasCliente);
        if (validarListaVacia(alojamientos)) {
            alojamientos = obtenerAlojamientosAleatorios();
        }
        return alojamientos;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(ArrayList<Deseo> deseosCliente) throws Exception {
        ArrayList<Alojamiento> alojamientos = alojamientoRepositorio.obtenerAlojamientosPorDeseosCliente(deseosCliente);
        if (validarListaVacia(alojamientos)) throw new Exception("No hay alojamientos agregados a tu lista de deseos de momento");
        return alojamientos;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosPorFiltro(TipoAlojamiento tipoAlojamiento, String nombre, String ciudad,
                                                               String precioMin, String precioMax) throws Exception {

        double minPrecio;
        double maxPrecio;
        try {
            minPrecio = precioMin == null || precioMin.isBlank() ? 0 : Double.parseDouble(precioMin);
        } catch (NumberFormatException e) {
            throw new Exception("El precio mínimo debe ser un número válido.");
        }
        try {
            maxPrecio = precioMax == null || precioMax.isBlank() ? 0 : Double.parseDouble(precioMax);
        } catch (NumberFormatException e) {
            throw new Exception("El precio máximo debe ser un número válido.");
        }

        if (minPrecio < 0 || maxPrecio < 0) throw new Exception("Los precios no pueden ser negativos.");
        if (maxPrecio > 0 && minPrecio > maxPrecio) throw new Exception("El precio mínimo no puede ser mayor al precio máximo.");
        if (nombre != null && !nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]*$")) throw new Exception("El nombre ingresado contiene caracteres inválidos.");

        return alojamientoRepositorio.obtenerAlojamientosPorFiltro(tipoAlojamiento, nombre, ciudad, minPrecio, maxPrecio);
    }


    public boolean validarListaVacia(ArrayList<Alojamiento> lista) {
        return lista == null || lista.isEmpty();
    }

    public ArrayList<Servicio> obtenerServiciosAlojamiento(String idAlojamiento) {
        return servicioRepositorio.obtenerServiciosAlojamientoPorId(idAlojamiento);
    }

    public ArrayList<Imagen> obtenerImagenesAlojamiento(String idAlojamiento) {
        return imagenRepositorio.obtenerImagenesAlojamientoPorId(idAlojamiento);
    }
}
