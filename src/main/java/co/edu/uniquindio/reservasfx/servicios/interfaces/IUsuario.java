package co.edu.uniquindio.reservasfx.servicios.interfaces;

import co.edu.uniquindio.reservasfx.modelo.entidades.Calificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.Notificacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IUsuario {

    void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia,
                          boolean activacion) throws Exception;

    void editarCliente(Cliente antiguo, String cedula, String nombre, String telefono, String direcccion, String email) throws Exception;

    void eliminarCliente(String cedula) throws Exception;

    Usuario iniciarSesion(String email, String contrasenia) throws Exception;

    String enviarCodigo(String email, boolean cambioContrasenia) throws Exception;

    void cambiarContrasenia(String cedula, String codigoCorrecto, String codigoIngresado, String contraseniaNueva,
                            String contraseniaVerificacion) throws Exception;

    void activarCuentaCliente(String cedula, String codigoCorrecto, String codigoIngresado) throws Exception;

    void enviarCalificacion(String cedulaCliente, String idAlojamiento, String comentario, String valoracion,
                            ArrayList<Reserva> reservasCliente) throws Exception;

    ArrayList<Calificacion> obtenerCalificacionesAlojamiento(String idAlojamiento) throws Exception;

    void enviarNotificacion(String cedulaCliente, String titulo, String mensaje) throws Exception;

    ArrayList<Notificacion> obtenerNotificacionesNoLeidas(String cedulaCliente) throws Exception;

    void marcarNotificacionComoLeida(Notificacion notificacion) throws Exception;

    void guardarDeseo(String cedulaCliente, String idAlojamiento) throws Exception;

    void eliminarDeseo(String cedulaCliente, String idAlojamiento) throws Exception;

    ArrayList<Deseo> obtenerDeseosCliente(String cedulaCliente) throws Exception;

    void recargarBilleteraCliente(String cedulaCliente, double monto) throws Exception;
}
