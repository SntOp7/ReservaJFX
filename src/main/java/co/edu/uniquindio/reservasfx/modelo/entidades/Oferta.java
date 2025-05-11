package co.edu.uniquindio.reservasfx.modelo.entidades;

import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Oferta {
    OfertaEspecial ofertaEspecial;
    String idAlojamiento;
    String nombre;
    String descripcion;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    double porcentajeDescuento;
}
