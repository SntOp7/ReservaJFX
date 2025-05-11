package co.edu.uniquindio.reservasfx.modelo.entidades.usuario;

import co.edu.uniquindio.reservasfx.modelo.entidades.BilleteraVirtual;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente extends Usuario {
    private BilleteraVirtual billetera;

    public Cliente(String cedula, String nombreCompleto, String telefono, String email, String contrasena, boolean activo) {
        super(cedula, nombreCompleto, telefono, email, contrasena, activo);
    }
}
