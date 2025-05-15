package co.edu.uniquindio.reservasfx.modelo;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;

public class SeleccionReserva {
    private static SeleccionReserva instancia;
    private Habitacion habitacionSeleccionada;

    private SeleccionReserva() {}

    public static SeleccionReserva getInstancia() {
        if (instancia == null) instancia = new SeleccionReserva();
        return instancia;
    }

    public Habitacion getHabitacionSeleccionada() {
        return habitacionSeleccionada;
    }

    public void setHabitacionSeleccionada(Habitacion habitacion) {
        this.habitacionSeleccionada = habitacion;
    }

    public void reiniciar() {
        habitacionSeleccionada = null;
    }
}
