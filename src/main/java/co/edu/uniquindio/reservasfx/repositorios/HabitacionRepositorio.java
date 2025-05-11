package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.util.ArrayList;

public class HabitacionRepositorio {
    private ArrayList<Habitacion> habitaciones;
    
    public HabitacionRepositorio() {
        habitaciones = new ArrayList<>();
    }

    public void agregar(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    public Habitacion buscarHabitacion(String idHotel, int numero) {
        return habitaciones.stream()
                .filter(h -> h.getNumero() == numero && h.getIdHotel().equals(idHotel))
                .findFirst()
                .orElse(null);
    }

    public void editar(Habitacion habitacion) {
        habitaciones.set(habitaciones.indexOf(habitacion), habitacion);
    }

    public void eliminar(Habitacion habitacion) {
        habitaciones.remove(habitacion);
    }


}
