package co.edu.uniquindio.reservasfx.modelo.enums;

import lombok.Getter;

public enum EstadoReserva {
    ACTIVA("Activa"),
    CANCELADA("Cancelada"),
    FINALIZADA("Finalizada");

    @Getter
    private final String nombre;

    EstadoReserva(String nombre) {
        this.nombre = nombre;
    }
}
