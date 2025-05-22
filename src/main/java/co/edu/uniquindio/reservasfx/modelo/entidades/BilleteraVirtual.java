package co.edu.uniquindio.reservasfx.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class BilleteraVirtual implements Serializable {
    private String id;
    private double saldo;

    public void recargar(double monto) {
        saldo += monto;
    }

    public void descontar(double monto) {
        saldo -= monto;
    }
}
