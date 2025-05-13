package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.utils.Persistencia;

import java.io.IOException;
import java.util.ArrayList;

public class ReservaRepositorio {
    private ArrayList<Reserva> reservas;

    public ReservaRepositorio() {
        this.reservas = leerDatos();
    }

    public void agregar(Reserva reserva) {
        reservas.add(reserva);
        guardarDatos(reservas);
    }

    public void cancelar(Reserva reserva) {
        reservas.remove(reserva);
        guardarDatos(reservas);
    }

    public Reserva buscarReservaPorId(String id) {
        return reservas.stream().
                filter(r -> r.getId().equals(id)).
                findFirst().
                orElse(null);
    }

    public void guardarDatos(ArrayList<Reserva> reservas) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_RESERVAS, reservas);
        } catch (IOException e) {
            System.err.println("Error guardando reservas: " + e.getMessage());
        }
    }


    public ArrayList<Reserva> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_RESERVAS);
            if (datos != null) {
                return (ArrayList<Reserva>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando reservas: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) {
        return null;
    }
}
