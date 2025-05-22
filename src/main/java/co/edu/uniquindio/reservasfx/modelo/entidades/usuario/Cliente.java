package co.edu.uniquindio.reservasfx.modelo.entidades.usuario;

import co.edu.uniquindio.reservasfx.modelo.entidades.BilleteraVirtual;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Cliente extends Usuario implements Serializable {
    private BilleteraVirtual billetera;

    public Cliente(String cedula, String nombreCompleto, String telefono, String direccion, String email, String contrasena, boolean activo) {
        super(cedula, nombreCompleto, telefono, direccion, email, contrasena, activo);
    }
}
