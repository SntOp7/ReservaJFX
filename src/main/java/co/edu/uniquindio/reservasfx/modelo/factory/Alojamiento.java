package co.edu.uniquindio.reservasfx.modelo.factory;

import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class Alojamiento implements Serializable {
    private String id;
    private String nombre;
    private Ciudad ciudad;
    private String descripcion;
    private double precioPorNoche;
    private int capacidadMaxima;
    private String imagenPrincipal;

    public String getTipo() {
        return this.getClass().getSimpleName();
    }
}
