package co.edu.uniquindio.reservasfx.modelo.enums;

import lombok.Getter;

public enum TipoServicio {
    WIFI("WiFi"),
    PISCINA("Piscina"),
    PARQUEADERO("Parqueadero"),
    DESAYUNO("Desayuno incluido"),
    AIRE_ACONDICIONADO("Aire acondicionado"),
    TV("Televisión"),
    GIMNASIO("Gimnasio"),
    MASCOTAS("Admite mascotas"),
    RESTAURANTE("Restaurante");

    @Getter
    private final String nombre;

    TipoServicio(String nombre) {
        this.nombre = nombre;
    }
}
