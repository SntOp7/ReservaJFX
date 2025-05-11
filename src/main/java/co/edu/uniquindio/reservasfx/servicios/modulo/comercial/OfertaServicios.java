package co.edu.uniquindio.reservasfx.servicios.modulo.comercial;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.repositorios.OfertaRepositorio;
import co.edu.uniquindio.reservasfx.servicios.ModuloComercialServicios;

import java.time.LocalDate;

public class OfertaServicios {

    private final OfertaRepositorio ofertaRepositorio;

    public OfertaServicios() {
        ofertaRepositorio = new OfertaRepositorio();
    }

    public void registrarOferta(OfertaEspecial ofertaEspecial, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) throws Exception {
        if (ofertaEspecial == null ) throw new Exception("La oferta es obligatoria");
        if (descripcion == null || descripcion.isEmpty()) throw new Exception("La descripcion es obligatoria");
        if (fechaInicio == null) throw new Exception("La fecha de inicio es obligatoria");
        if (fechaFin == null) throw new Exception("La fecha de fin es obligatoria");
        if (porcentajeDescuento == 0) throw new Exception("El porcentaje de descuento es obligatorio");

        Oferta oferta = Oferta.builder().ofertaEspecial(ofertaEspecial).descripcion(descripcion).fechaInicio(fechaInicio).fechaFin(fechaFin).porcentajeDescuento(porcentajeDescuento).build();

        ofertaRepositorio.agregar(oferta);
    }

    public void editarOferta(OfertaEspecial ofertaEspecial, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeDescuento) {

        Oferta ofertaEditada = Oferta.builder().ofertaEspecial(ofertaEspecial).descripcion(descripcion).fechaInicio(fechaInicio).fechaFin(fechaFin).porcentajeDescuento(porcentajeDescuento).build();


        ofertaRepositorio.editar(ofertaEditada);
    }

    public void eliminarOferta(String nombre) throws Exception {
        Oferta oferta = buscarOferta(nombre);
        if (oferta == null) throw new Exception("La Oferta no existe");
        ofertaRepositorio.eliminar(oferta);
    }

    public Oferta buscarOferta(String nombre){
        return ofertaRepositorio.buscarOferta(nombre);
    }
}
