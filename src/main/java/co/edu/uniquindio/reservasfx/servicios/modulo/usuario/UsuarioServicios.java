package co.edu.uniquindio.reservasfx.servicios.modulo.usuario;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.DeseoRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.reservasfx.servicios.EmpresaServicio;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class UsuarioServicios {

    private final UsuarioRepositorio usuarioRepositorio;
    private final DeseoRepositorio deseoRepositorio;
    private final NotificacionServicios notificacionServicios;
    private final AlojamientoServicios alojamientoServicios;

    public UsuarioServicios(NotificacionServicios notificacionServicios, AlojamientoServicios alojamientoServicios) {
        usuarioRepositorio = new UsuarioRepositorio();
        deseoRepositorio = new DeseoRepositorio();
        this.notificacionServicios = notificacionServicios;
        this.alojamientoServicios = alojamientoServicios;
    }

    public void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia, boolean activacion) throws Exception {
        verificarCampos(cedula, nombre, telefono, direccion, email);
        if (contrasenia == null || contrasenia.isEmpty()) throw new Exception("La contraseña es obligatoria");
        if (usuarioRepositorio.buscarCliente(cedula) != null) throw new Exception("Ya existe un usuario con dicha cedula");

        Cliente cliente = new Cliente(cedula, nombre, telefono, email, contrasenia, activacion);
        BilleteraVirtual billeteraVirtual = new BilleteraVirtual(UUID.randomUUID().toString(), 0);
        cliente.setBilletera(billeteraVirtual);
        usuarioRepositorio.agregar(cliente);
    }

    public void editarCliente(Cliente clienteAntiguo, String cedula, String nombre, String telefono, String direcccion,
                              String email) throws Exception {
        verificarCampos(cedula, nombre, telefono, direcccion, email);
        if (clienteAntiguo.getCedula().equals(cedula)) {
            Cliente cliente = usuarioRepositorio.buscarCliente(cedula);
            cliente.setNombre(nombre);
            cliente.setTelefono(telefono);
            cliente.setDireccion(direcccion);
            cliente.setEmail(email);
            usuarioRepositorio.editar(cliente);
        } else if (usuarioRepositorio.buscarCliente(cedula) != null) {
            throw new Exception("Ya existe un usuario con la misma cedula");
        } else {
            clienteAntiguo.setCedula(cedula);
            clienteAntiguo.setNombre(nombre);
            clienteAntiguo.setTelefono(telefono);
            clienteAntiguo.setDireccion(direcccion);
            clienteAntiguo.setEmail(email);
            usuarioRepositorio.editar(clienteAntiguo);
        }
        notificacionServicios.enviarNotificacion(cedula, "Actualización de Datos",
                Constantes.ACTUALIZACION_DATOS_CLIENTE());
    }

    private void verificarCampos(String cedula, String nombre, String telefono, String direcccion, String email) throws Exception {
        if (cedula == null || cedula.isEmpty()) throw new Exception("La cedula es obligatoria");
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (telefono == null || telefono.isEmpty()) throw new Exception("El telefono es obligatorio");
        if (direcccion == null || direcccion.isEmpty()) throw new Exception("La direccion es obligatoria");
        if (email == null || email.isEmpty()) throw new Exception("El email es obligatorio");

        if (cedula.length() != 10) throw new Exception("La cedula debe tener 10 digitos");
        if (!telefono.matches("^3\\d{9}$")) throw new Exception("Verifique el numero de telefono");
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) throw new Exception("Verifique el formato del email");
    }

    public void eliminarCliente(String cedula) throws Exception {
        Cliente cliente = usuarioRepositorio.buscarCliente(cedula);
        if (cliente == null) throw new Exception("El cliente no existe");
        usuarioRepositorio.eliminar(cliente);
    }

    public Cliente buscarClientePorCedula(String cedula) {
        return usuarioRepositorio.buscarCliente(cedula);
    }

    public Usuario iniciarSesion(String email, String contrasenia) throws Exception {
        if (email == null || email.isEmpty()) throw new Exception("El email es obligatorio");
        if (contrasenia == null || contrasenia.isEmpty()) throw new Exception("La contraseña es obligatoria");

        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);

        if (usuario == null) throw new Exception("El usuario no existe");
        if (!usuario.getContrasenia().equals(contrasenia)) throw new Exception("La contraseña es incorrecta");

        return usuario;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepositorio.buscarUsuarioPorEmail(email);
    }

    public void cambiarContrasenia(String email, String codigoCorrecto, String codigoIngresado, String contraseniaNueva,
                                   String contraseniaVerificacion) throws Exception {
        if (codigoIngresado == null || codigoIngresado.isEmpty()) throw new Exception("El codigo es obligatorio");
        if (contraseniaNueva == null || contraseniaNueva.isEmpty()) throw new Exception("La contraseña nueva es obligatoria");
        if (contraseniaVerificacion == null || contraseniaVerificacion.isEmpty())
            throw new Exception("La verificacion de la nueva contraseña es obligatoria");

        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);
        if (usuario == null) throw new Exception("El usuario no existe");

        if (!contraseniaNueva.equals(contraseniaVerificacion)) throw new Exception("Debes verificar la nueva contraseña");
        if (!codigoCorrecto.equals(codigoIngresado)) throw new Exception("El código de verificación es incorrecto");

        usuario.setContrasenia(contraseniaNueva);
        notificacionServicios.enviarNotificacion(usuario.getContrasenia(), "Cambio Contraseña",
                Constantes.CAMBIO_CONTRASENA());
    }

    public void activarCuentaCliente(String cedula, String codigoCorrecto, String codigoIngresado) throws Exception {
        if (codigoIngresado == null || codigoIngresado.isEmpty()) throw new Exception("El codigo es obligatorio");

        Cliente cliente = usuarioRepositorio.buscarCliente(cedula);
        if (cliente == null) throw new Exception("El cliente no existe");

        if (!codigoCorrecto.equals(codigoIngresado)) throw new Exception("El código de activación es incorrecto");

        cliente.setActivo(true);
    }

    public void guardarDeseo(String cedulaCliente, String idAlojamiento) throws Exception {
        Deseo deseo = new Deseo(cedulaCliente, idAlojamiento);
        deseoRepositorio.agregar(deseo);
        Alojamiento alojamiento = alojamientoServicios.buscarAlojamientoPorId(idAlojamiento);
        notificacionServicios.enviarNotificacion(cedulaCliente, "Deseo Agregado",
                Constantes.DESEO_AGREGADO(alojamiento.getNombre()));
    }

    public void eliminarDeseo(String cedulaCliente, String idAlojamiento) throws Exception {
        Deseo deseo = deseoRepositorio.buscarDeseo(cedulaCliente, idAlojamiento);
        if (deseo == null) throw new Exception("El deseo no existe");
        deseoRepositorio.eliminar(deseo);
        Alojamiento alojamiento = alojamientoServicios.buscarAlojamientoPorId(idAlojamiento);
        notificacionServicios.enviarNotificacion(cedulaCliente, "Deseo Removido",
                Constantes.DESEO_REMOVIDO(alojamiento.getNombre()));
    }

    public ArrayList<Deseo> obtenerDeseosCliente(String cedulaCliente) {
        return deseoRepositorio.obtenerDeseosPorCedula(cedulaCliente);
    }

    public void recargarBilleteraCliente(String cedulaCliente, double monto) throws Exception {
        Cliente cliente = usuarioRepositorio.buscarCliente(cedulaCliente);
        if (cliente == null) throw new Exception("El cliente no existe");
        cliente.getBilletera().setSaldo(cliente.getBilletera().getSaldo() + monto);
        usuarioRepositorio.editar(cliente);
    }

    public ArrayList<Cliente> obtenerClientes() {
        return usuarioRepositorio.getClientes();
    }
}
