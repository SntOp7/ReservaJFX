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

    public Reserva buscarReservaPorId(String id) {
        return reservas.stream().
                filter(r -> r.getId().equals(id)).
                findFirst().
                orElse(null);
    }
}
