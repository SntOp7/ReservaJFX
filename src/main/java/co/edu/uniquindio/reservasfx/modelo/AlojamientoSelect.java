package co.edu.uniquindio.reservasfx.modelo;

import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import lombok.Getter;
import lombok.Setter;

public class AlojamientoSelect {
    public static AlojamientoSelect INSTANCIA;

    @Getter
    @Setter
    private Alojamiento alojamiento;

    private AlojamientoSelect() {
    }

    public static AlojamientoSelect getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new AlojamientoSelect();
        }
        return INSTANCIA;
    }

    public void reiniciar() {
        alojamiento = null;
    }
}
