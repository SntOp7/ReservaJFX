package co.edu.uniquindio.reservasfx.modelo.entidades.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public abstract class Usuario implements Serializable {
    private String cedula;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;
    private String contrasenia;
    private boolean activo;
}
