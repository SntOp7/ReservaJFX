package co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento;

import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Servicio {
    private String idAlojamiento;
    private String nombre;
    private TipoServicio tipo;

    public Servicio(String idAlojamiento, TipoServicio tipo) {
        this.idAlojamiento = idAlojamiento;
        this.tipo = tipo;
        this.nombre = tipo.getNombre();
    }
}
