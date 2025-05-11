package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IUsuario {

    void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia,
                          boolean activacion) throws Exception;

    void editarCliente(String cedula, String nombre, String telefono, String email, String contrasenia) throws Exception;

    void eliminarCliente(String cedula) throws Exception;

    ArrayList<Cliente> getClientes();

    void registrarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception;

    ArrayList<Calificacion> obtenerCalificacionesAlojamiento(String idAlojamiento) throws Exception;

    void registrarNotificacion(String cedulaCliente, String titulo, String mensaje, LocalDateTime fecha, boolean leida) throws Exception;

    ArrayList<Notificacion> obtenerNotificacionesCliente(String cedulaCliente) throws Exception;

    Usuario iniciarSesion(String email, String contrasenia) throws Exception;
}
