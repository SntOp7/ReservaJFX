package co.edu.uniquindio.reservasfx.modelo.enums;

import lombok.Getter;

public enum TipoAlojamiento {
    CASA("Casa"),
    APARTAMENTO("Apartamento"),
    HOTEL("Hotel");

    @Getter
    private final String nombre;

    TipoAlojamiento(String nombre) {
        this.nombre = nombre;
    }
}
