package co.edu.uniquindio.reservasfx.modelo.factory;

import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public abstract class Alojamiento {
    private String id;
    private String nombre;
    private Ciudad ciudad;
    private String descripcion;
    private double precioPorNoche;
    private int capacidadMaxima;
    private String imagenPrincipal;
    private int visitas;
}
