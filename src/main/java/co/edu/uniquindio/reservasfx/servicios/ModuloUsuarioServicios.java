package co.edu.uniquindio.reservasfx.servicios;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IUsuario;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.CalificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.NotificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.UsuarioServicios;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModuloUsuarioServicios implements IUsuario {

    private final UsuarioServicios usuarioServicios;
    private final CalificacionServicios calificacionServicios;
    private final NotificacionServicios notificacionServicios;

    public ModuloUsuarioServicios() {
        usuarioServicios = new UsuarioServicios();
        calificacionServicios = new CalificacionServicios();
        notificacionServicios = new NotificacionServicios();
    }

    @Override
    public void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia, boolean activacion) throws Exception {
        usuarioServicios.registrarCliente(cedula, nombre, telefono, direccion, email, contrasenia, activacion);
    }

    @Override
    public void editarCliente(String cedula, String nombre, String telefono, String email, String contrasenia) throws Exception{
        usuarioServicios.editarCliente(cedula, nombre, telefono, email, contrasenia);
    }

    @Override
    public void eliminarCliente(String cedula) throws Exception {
        usuarioServicios.eliminarCliente(cedula);
    }

    @Override
    public void registrarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception {
        calificacionServicios.registrarCalificacion(nombreCliente, nombreAlojamiento, comentario, valoracion);
    }

    @Override
    public ArrayList<Calificacion> obtenerCalificacionesAlojamiento(String idAlojamiento) throws Exception {
        return calificacionServicios.obtenerCalificacionesAlojamiento(idAlojamiento);
    }

    @Override
    public void registrarNotificacion(String cedulaCliente, String titulo, String mensaje, LocalDateTime fecha, boolean leida) throws Exception {
        notificacionServicios.registrarNotificacion(cedulaCliente, titulo, mensaje, fecha, leida);
    }

    @Override
    public ArrayList<Notificacion> obtenerNotificacionesCliente(String cedulaCliente) throws Exception {
        return notificacionServicios.obtenerNotificacionesCLiente(cedulaCliente);
    }

    @Override
    public Usuario iniciarSesion(String email, String contrasenia) throws Exception {
        return usuarioServicios.iniciarSesion(email, contrasenia);
    }

    @Override
    public ArrayList<Cliente> getClientes() {
        return usuarioServicios.getClientes();
    }
}
