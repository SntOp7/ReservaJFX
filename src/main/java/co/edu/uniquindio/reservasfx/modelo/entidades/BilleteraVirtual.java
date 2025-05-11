package co.edu.uniquindio.reservasfx.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BilleteraVirtual {
    private String id;
    private double saldo;

    public void recargar(double monto) {
        saldo += monto;
    }

    public void descontar(double monto) {
        saldo -= monto;
    }
}
