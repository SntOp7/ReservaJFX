package co.edu.uniquindio.reservasfx.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Calificacion implements Serializable {
    private String cedulaCliente;
    private String idAlojamiento;
    private String comentario;
    private int valoracion;
}
