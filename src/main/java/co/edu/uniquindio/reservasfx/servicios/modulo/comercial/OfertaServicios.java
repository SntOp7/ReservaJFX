package co.edu.uniquindio.reservasfx.servicios.modulo.comercial;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoOferta;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.repositorios.OfertaRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.NotificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.UsuarioServicios;
import org.simplejavamail.api.internal.clisupport.model.Cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class OfertaServicios {

    private final OfertaRepositorio ofertaRepositorio;
    private final UsuarioServicios usuarioServicios;
    private final NotificacionServicios notificacionServicios;

    public OfertaServicios(UsuarioServicios usuarioServicios, NotificacionServicios notificacionServicios) {
        ofertaRepositorio = new OfertaRepositorio();
        this.usuarioServicios = usuarioServicios;
        this.notificacionServicios = notificacionServicios;
    }

    public void registrarOferta(String ofertaEspecialString, String idAlojamiento, String nombre, String descripcion,
                                LocalDate fechaInicio, LocalDate fechaFin, String porcentajeDescuentoString) throws Exception {

        if (ofertaEspecialString == null || ofertaEspecialString.isEmpty()) throw new Exception("Debes seleccionar una oferta especial");
        OfertaEspecial ofertaEspecial = OfertaEspecial.valueOf(ofertaEspecialString.toUpperCase().replaceAll(" ", "_"));
        verificarCampos(nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuentoString);
        double porcentaje = 0;
        try {
            porcentaje = Double.parseDouble(porcentajeDescuentoString);
        } catch (NumberFormatException e) {
            throw new Exception("El porcentaje de descuento debe ser un numero");
        }
        if (porcentaje < 0 || porcentaje > 100) throw new Exception("El porcentaje de descuento debe ser mayor a cero y menor a 100");
        EstadoOferta estadoOferta = obtenerEstadoOferta(fechaInicio, fechaFin);
        Oferta oferta = Oferta.builder().ofertaEspecial(ofertaEspecial).id(UUID.randomUUID().toString()).
                idAlojamiento(idAlojamiento).nombre(nombre).descripcion(descripcion).fechaInicio(fechaInicio).
                fechaFin(fechaFin).porcentajeDescuento(porcentaje).estado(estadoOferta).build();

        ofertaRepositorio.agregar(oferta);
        for (Cliente cliente : usuarioServicios.obtenerClientes()) {
            notificacionServicios.enviarNotificacion(cliente.getCedula(), "Nueva Oferta",
                    Constantes.NUEVA_OFERTA(idAlojamiento));
        }
    }

    private EstadoOferta obtenerEstadoOferta(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(LocalDate.now())) return EstadoOferta.PROXIMA;
        if (fechaInicio.isEqual(LocalDate.now())) return EstadoOferta.ACTIVA;
        return null;
    }

    public void editarOferta(String id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                             String porcentajeDescuentoString) throws Exception {
        verificarCampos(nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuentoString);
        double porcentaje = 0;
        try {
            porcentaje = Double.parseDouble(porcentajeDescuentoString);
        } catch (NumberFormatException e) {
            throw new Exception("El porcentaje de descuento debe ser un numero");
        }
        if (porcentaje < 0 || porcentaje > 100) throw new Exception("El porcentaje de descuento debe ser mayor a cero y menor a 100");
        Oferta oferta = ofertaRepositorio.buscarOfertaPorId(id);
        if (oferta == null) {
            throw new Exception("La oferta no existe");
        }
        oferta.setNombre(nombre);
        oferta.setDescripcion(descripcion);
        oferta.setFechaInicio(fechaInicio);
        oferta.setFechaFin(fechaFin);
        oferta.setPorcentajeDescuento(porcentaje);
        ofertaRepositorio.editar(oferta);
    }

    private void verificarCampos(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, String porcentajeDescuento) throws Exception {
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (descripcion == null || descripcion.isEmpty()) throw new Exception("La descripcion es obligatoria");
        if (fechaInicio == null) throw new Exception("La fecha de inicio es obligatoria");
        if (fechaFin == null) throw new Exception("La fecha de fin es obligatoria");
        if (fechaInicio.isBefore(LocalDate.now())) throw new Exception("La fecha de inicio no puede ser en el pasado");
        if (fechaFin.isBefore(fechaInicio)) throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio");
        if (porcentajeDescuento == null ||  porcentajeDescuento.isEmpty()) throw  new Exception("El porcentaje de descuento es obligatorio");
    }

    public void eliminarOferta(String id) throws Exception {
        Oferta oferta = buscarOferta(id);
        if (oferta == null) throw new Exception("La Oferta no existe");
        ofertaRepositorio.eliminar(oferta);
    }

    public Oferta buscarOferta(String id){
        return ofertaRepositorio.buscarOfertaPorId(id);
    }

    public ArrayList<Oferta> obtenerTodasOfertas() {
        return ofertaRepositorio.getOfertas();
    }

    public ArrayList<Oferta> obtenerOfertas(String idAlojamiento) {
        return ofertaRepositorio.obtenerOfertasAlojamiento(idAlojamiento);
    }

    public ArrayList<Oferta> obtenerOfertasActivasAlojamiento(String idAlojamiento) {
        return ofertaRepositorio.obtenerOfertasActivasAlojamiento(idAlojamiento);
    }

    public void actualizarEstadoOfertas() {
        ofertaRepositorio.actualizarEstadoOfertas();
    }
}
