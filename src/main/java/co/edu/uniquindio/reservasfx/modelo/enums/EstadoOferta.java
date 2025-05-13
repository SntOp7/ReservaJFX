package co.edu.uniquindio.reservasfx.modelo.enums;

import lombok.Getter;

public enum EstadoOferta {
    PROXIMA("Próxima"),
    ACTIVA("Activa"),
    CADUCADA("Caducada");

    @Getter
    private final String nombre;

    EstadoOferta(String nombre) {
        this.nombre = nombre;
    }
}
