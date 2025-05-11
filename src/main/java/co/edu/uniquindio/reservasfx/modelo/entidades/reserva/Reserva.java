package co.edu.uniquindio.reservasfx.modelo.entidades.reserva;


import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Reserva {
    private String id;
    private String cedulaCliente;
    private String idAlojamiento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int numeroHuespedes;
    private Factura factura;
    private EstadoReserva estado;
}
