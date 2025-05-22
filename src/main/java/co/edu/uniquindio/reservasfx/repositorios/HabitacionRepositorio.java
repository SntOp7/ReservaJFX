package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class HabitacionRepositorio {
    private ArrayList<Habitacion> habitaciones;
    
    public HabitacionRepositorio() {
        this.habitaciones = leerDatos();
        System.out.println("Habitaciones cargados desde archivo: " + habitaciones.size());
    }

    public void agregar(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    public void editar(Habitacion habitacion) {
        habitaciones.set(habitaciones.indexOf(habitacion), habitacion);
    }

    public void eliminar(Habitacion habitacion) {
        habitaciones.remove(habitacion);
    }

    public Habitacion buscarHabitacion(String idHotel, int numero) {
        return habitaciones.stream()
                .filter(h -> h.getNumero() == numero && h.getIdHotel().equals(idHotel))
                .findFirst()
                .orElse(null);
    }

    public ArrayList<Habitacion> obtenerHabitacionesPorIdHotel(String idHotel) {
        ArrayList<Habitacion> habitacionesPorIdHotel = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdHotel().equals(idHotel)) {
                habitacionesPorIdHotel.add(habitacion);
            }
        }
        return habitacionesPorIdHotel;
    }

    public void guardarDatos(ArrayList<Habitacion> habitaciones) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_HABITACIONES, habitaciones);
            System.out.println("Habitaciones guardados en archivo: " + habitaciones.size());
        } catch (IOException e) {
            System.err.println("Error guardando habitaciones: " + e.getMessage());
        }
    }


    public ArrayList<Habitacion> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_HABITACIONES);
            if (datos != null) {
                return (ArrayList<Habitacion>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando habitaciones: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
