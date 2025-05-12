package co.edu.uniquindio.reservasfx.servicios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IUsuario;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.CalificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.NotificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.UsuarioServicios;
import co.edu.uniquindio.reservasfx.utils.EnvioEmail;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModuloUsuarioServicios implements IUsuario {

    private EmpresaServicio empresaServicio = EmpresaServicio.getInstancia();
    private final UsuarioServicios usuarioServicios;
    private final CalificacionServicios calificacionServicios;
    private final NotificacionServicios notificacionServicios;

    public ModuloUsuarioServicios() {
        usuarioServicios = new UsuarioServicios();
        calificacionServicios = new CalificacionServicios();
        notificacionServicios = new NotificacionServicios();
    }

    @Override
    public void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia,
                                 boolean activacion) throws Exception {
        usuarioServicios.registrarCliente(cedula, nombre, telefono, direccion, email, contrasenia, activacion);
    }

    @Override
    public void editarCliente(Cliente antiguo, String cedula, String nombre, String telefono, String direcccion, String email) throws Exception {
        usuarioServicios.editarCliente(antiguo, cedula, nombre, telefono, direcccion, email);
    }

    @Override
    public void eliminarCliente(String cedula) throws Exception {
        usuarioServicios.eliminarCliente(cedula);
    }

    @Override
    public Usuario iniciarSesion(String email, String contrasenia) throws Exception {
        return usuarioServicios.iniciarSesion(email, contrasenia);
    }

    @Override
    public String enviarCodigo(String email, boolean cambioContrasenia) throws Exception {
        int numero = (int) (Math.random() * 1_000_000);
        String codigo = String.format("%06d", numero);
        Usuario usuario = usuarioServicios.buscarUsuarioPorEmail(email);
        String mensaje = Constantes.ENVIO_CODIGO(codigo, usuario.getNombre());
        if (cambioContrasenia) {
            enviarNotificacion(usuario.getCedula(), "Código de Verificación", Constantes.CODIGO_VERIFICACION(codigo));
        }
        EnvioEmail.enviarNotificacion(email, "Código de verificación para solicitud en BookYourStay", mensaje);
        return codigo;
    }

    @Override
    public void cambiarContrasenia(String email, String codigoCorrecto, String codigoIngresado, String contraseniaNueva,
                                   String contraseniaVerificacion) throws Exception {
        usuarioServicios.cambiarContrasenia(email, codigoCorrecto, codigoIngresado, contraseniaNueva, contraseniaVerificacion);
    }

    @Override
    public void activarCuentaCliente(String cedula, String codigoCorrecto, String codigoIngresado) throws Exception {
        usuarioServicios.activarCuentaCliente(cedula, codigoCorrecto, codigoIngresado);
    }

    @Override
    public void enviarCalificacion(String nombreCliente, String nombreAlojamiento, String comentario, int valoracion) throws Exception {
        calificacionServicios.enviarCalificacion(nombreCliente, nombreAlojamiento, comentario, valoracion);
    }

    @Override
    public ArrayList<Calificacion> obtenerCalificacionesAlojamiento(String nombreAlojamiento) throws Exception {
        return calificacionServicios.obtenerCalificacionesAlojamiento(nombreAlojamiento);
    }

    @Override
    public void enviarNotificacion(String cedulaCliente, String titulo, String mensaje) throws Exception {
        notificacionServicios.enviarNotificacion(cedulaCliente, titulo, mensaje);
    }

    @Override
    public ArrayList<Notificacion> obtenerNotificacionesNoLeidas(String cedulaCliente) throws Exception {
        return notificacionServicios.obtenerNotificacionesNoLeidas(cedulaCliente);
    }

    @Override
    public void marcarNotificacionComoLeida(Notificacion notificacion) throws Exception {
        notificacionServicios.marcarNotificacionComoLeida(notificacion);
    }
}
