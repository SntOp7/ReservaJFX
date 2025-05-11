package co.edu.uniquindio.reservasfx.modelo.entidades.reserva;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Factura {
    private String id;
    private double subtotal;
    private double total;
    private LocalDate fecha;
    private String codigoQR;
}
