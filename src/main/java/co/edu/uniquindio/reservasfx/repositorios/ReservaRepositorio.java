package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.util.ArrayList;

public class ReservaRepositorio {
    private ArrayList<Reserva> reservas;

    public ReservaRepositorio() {
        reservas = new ArrayList<>();
    }

    public void agregar(Reserva reserva) {
        reservas.add(reserva);
    }

    public void cancelar(Reserva reserva) {
        reservas.remove(reserva);
    }

    public Reserva buscarReserva(String cedulaCliente) {
        return reservas.stream().filter(e -> e.getCliente().getCedula().equals(cedulaCliente)).findFirst().orElse(null);
    }

    public ArrayList<Reserva> getReservas() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        for(Reserva reserva : reservas) {
            if (reserva.getEstado().equals(EstadoReserva.ACTIVA)) {
                reservas.add(reserva);
            }
        }
        return reservas;
    }
}
