package co.edu.uniquindio.reservasfx.modelo.entidades.usuario;

import co.edu.uniquindio.reservasfx.servicios.interfaces.IUsuario;

import java.io.Serializable;

public class Administrador extends Usuario implements Serializable {

    public Administrador(String cedula, String nombreCompleto, String telefono, String direccion, String email, String contrasena, boolean activo) {
        super(cedula, nombreCompleto, telefono, direccion, email, contrasena, activo);
    }
}
