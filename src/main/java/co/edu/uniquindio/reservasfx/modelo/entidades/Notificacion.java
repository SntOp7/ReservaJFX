package co.edu.uniquindio.reservasfx.modelo.entidades;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Notificacion {
    private String cedulaCliente;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    private boolean leida;
}
