package co.edu.uniquindio.reservasfx.modelo.enums;

import lombok.Getter;

public enum OfertaEspecial {
    ESTANCIA_PROLONGADA("Estancia Prolongada"),
    TARIFA_PROMOCIONAL("Tarifa Promocional");

    @Getter
    private final String nombre;

    OfertaEspecial(String nombre) {
        this.nombre = nombre;
    }
}
