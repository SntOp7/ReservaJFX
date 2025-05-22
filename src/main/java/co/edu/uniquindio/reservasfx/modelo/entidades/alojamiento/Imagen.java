package co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Imagen implements Serializable {
    private String idAlojamiento;
    private String ruta;
}
