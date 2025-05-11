package co.edu.uniquindio.reservasfx.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Calificacion {
    private String nombreCliente;
    private String nombreAlojamiento;
    private String comentario;
    private int valoracion;
}
