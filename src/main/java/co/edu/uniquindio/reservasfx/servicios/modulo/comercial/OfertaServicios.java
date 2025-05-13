package co.edu.uniquindio.reservasfx.servicios.modulo.comercial;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.repositorios.OfertaRepositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class OfertaServicios {

    private final OfertaRepositorio ofertaRepositorio;

    public OfertaServicios() {
        ofertaRepositorio = new OfertaRepositorio();
    }

    public void registrarOferta(OfertaEspecial ofertaEspecial, String idAlojamiento, String nombre, String descripcion,
                                LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) throws Exception {

        if (ofertaEspecial == null ) throw new Exception("La oferta es obligatoria");
        verificarCampos(nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuento);

        Oferta oferta = Oferta.builder().ofertaEspecial(ofertaEspecial).id(UUID.randomUUID().toString()).
                idAlojamiento(idAlojamiento).nombre(nombre).descripcion(descripcion).fechaInicio(fechaInicio).
                fechaFin(fechaFin).porcentajeDescuento(porcentajeDescuento).build();

        ofertaRepositorio.agregar(oferta);
    }

    public void editarOferta(String id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                             double porcentajeDescuento) throws Exception {
        verificarCampos(nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
        Oferta oferta = ofertaRepositorio.buscarOfertaPorId(id);
        oferta.setNombre(nombre);
        oferta.setDescripcion(descripcion);
        oferta.setFechaInicio(fechaInicio);
        oferta.setFechaFin(fechaFin);
        oferta.setPorcentajeDescuento(porcentajeDescuento);
        ofertaRepositorio.editar(oferta);
    }

    private void verificarCampos(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) throws Exception {
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (descripcion == null || descripcion.isEmpty()) throw new Exception("La descripcion es obligatoria");
        if (fechaInicio == null) throw new Exception("La fecha de inicio es obligatoria");
        if (fechaFin == null) throw new Exception("La fecha de fin es obligatoria");
        if (porcentajeDescuento == 0) throw new Exception("El porcentaje de descuento es obligatorio");
    }

    public void eliminarOferta(String id) throws Exception {
        Oferta oferta = buscarOferta(id);
        if (oferta == null) throw new Exception("La Oferta no existe");
        ofertaRepositorio.eliminar(oferta);
    }

    public Oferta buscarOferta(String id){
        return ofertaRepositorio.buscarOfertaPorId(id);
    }

    public ArrayList<Oferta> obtenerOfertas(String idAlojamiento) {
        return ofertaRepositorio.obtenerOfertasAlojamiento(idAlojamiento);
    }
}
