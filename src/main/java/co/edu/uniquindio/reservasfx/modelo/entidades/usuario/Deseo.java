package co.edu.uniquindio.reservasfx.modelo.entidades.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Deseo implements Serializable {
    private String cedulaCliente;
    private String idAlojamiento;
}
