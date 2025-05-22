package co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Habitacion implements Serializable {
    private String idHotel;
    private int numero;
    private double precio;
    private int capacidad;
    private String descripcion;
    private String imagen;
}
