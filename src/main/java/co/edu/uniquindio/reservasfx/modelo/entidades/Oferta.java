package co.edu.uniquindio.reservasfx.modelo.entidades;

import co.edu.uniquindio.reservasfx.modelo.enums.EstadoOferta;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Oferta implements Serializable {
    private OfertaEspecial ofertaEspecial;
    private String id;
    private String idAlojamiento;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double porcentajeDescuento;
    private EstadoOferta estado;
}
