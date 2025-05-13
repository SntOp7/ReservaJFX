package co.edu.uniquindio.reservasfx.modelo.entidades.reserva;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Factura {
    private String id;
    private double subtotal;
    private double total;
    private LocalDateTime fecha;
    private String codigoQR;
}
