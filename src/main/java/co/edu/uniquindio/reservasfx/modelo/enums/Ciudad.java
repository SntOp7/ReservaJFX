package co.edu.uniquindio.reservasfx.modelo.enums;

import lombok.Getter;

public enum Ciudad {
    BOGOTA("Bogota"),
    MEDELLIN("Medellin"),
    CALI("Cali"),
    CARTAGENA("Cartagena"),
    BARRANQUILLA("Barranquilla"),
    PEREIRA("Pereira"),
    MANIZALES("Manizales"),
    ARMENIA("Armenia"),
    SANTA_MARTA("Santa Marta");

    @Getter
    private final String nombre;

    Ciudad(String nombre) {
        this.nombre = nombre;
    }
}
