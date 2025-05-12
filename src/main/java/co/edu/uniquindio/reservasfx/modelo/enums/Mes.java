package co.edu.uniquindio.reservasfx.modelo.enums;

import lombok.Getter;

public enum Mes {
    ENERO("Enero", 1),
    FEBRERO("Febrero", 2),
    MARZO("Marzo", 3),
    ABRIL("Abril", 4),
    MAYO("Mayo", 5),
    JUNIO("Junio", 6),
    JULIO("Julio", 7),
    AGOSTO("Agosto", 8),
    SEPTIEMBRE("Septiembre", 9),
    OCTUBRE("Octubre", 10),
    NOVIEMBRE("Noviembre", 11),
    DICIEMBRE("Diciembre", 12);

    @Getter
    private final String nombre;

    @Getter
    private final int numero;

    Mes(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
    }
}
