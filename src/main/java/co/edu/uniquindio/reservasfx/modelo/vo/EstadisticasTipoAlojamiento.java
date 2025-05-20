package co.edu.uniquindio.reservasfx.modelo.vo;

import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadisticasTipoAlojamiento {
    private TipoAlojamiento tipo;
    private double rentabilidad;
}
