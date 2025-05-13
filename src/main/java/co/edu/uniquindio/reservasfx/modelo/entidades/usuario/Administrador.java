package co.edu.uniquindio.reservasfx.modelo.entidades.usuario;

public class Administrador extends Usuario {

    public Administrador(String cedula, String nombreCompleto, String telefono, String direccion, String email, String contrasena, boolean activo) {
        super(cedula, nombreCompleto, telefono, direccion, email, contrasena, activo);
    }
}
