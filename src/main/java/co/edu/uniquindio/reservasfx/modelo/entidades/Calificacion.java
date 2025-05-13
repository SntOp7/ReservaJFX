package co.edu.uniquindio.reservasfx.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Calificacion {
    private String cedulaCliente;
    private String idAlojamiento;
    private String comentario;
    private int valoracion;
}
