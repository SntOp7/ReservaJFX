package co.edu.uniquindio.reservasfx.servicios.modulo.usuario;

import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.repositorios.UsuarioRepositorio;

import java.util.ArrayList;

public class UsuarioServicios {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicios() {
        usuarioRepositorio = new UsuarioRepositorio();
    }

    public void registrarCliente(String cedula, String nombre, String telefono, String direccion, String email, String contrasenia, boolean activacion) throws Exception {
        if (cedula == null || cedula.isEmpty()) throw new Exception("La cedula es obligatoria");
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (telefono == null || telefono.isEmpty()) throw new Exception("El telefono es obligatorio");
        if (direccion == null || direccion.isEmpty()) throw new Exception("La direccion es obligatoria");
        if (email == null || email.isEmpty()) throw new Exception("El email es obligatorio");
        if (contrasenia == null || contrasenia.isEmpty()) throw new Exception("La contraseña es obligatoria");

        if (cedula.length() != 10) throw new Exception("La cedula debe tener 10 digitos");
        if (!telefono.matches("^3\\d{9}$")) throw new Exception("Verifique el numero de telefono");
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) throw new Exception("Verifique el formato del email");

        Cliente cliente = new Cliente(cedula, nombre, telefono, email, contrasenia, activacion);
        usuarioRepositorio.agregar(cliente);
    }

    public void editarCliente(String cedula, String nombre, String telefono, String email, String contrasenia) throws Exception {
        Cliente clienteExistente = buscarCliente(cedula);

        if (clienteExistente == null) {
            throw new Exception("Habitación no encontrada");
        }

        Cliente cliente = new Cliente(cedula, nombre, telefono, email, contrasenia, true);

        usuarioRepositorio.editar(cliente);
    }

    public void eliminarCliente(String cedula) throws Exception {
        Cliente cliente = buscarCliente(cedula);
        if (cliente == null) throw new Exception("El cliente no existe");
        usuarioRepositorio.eliminar(cliente);
    }

    public ArrayList<Cliente> getClientes() {
        return usuarioRepositorio.getClientes();
    }

    public Cliente buscarCliente(String cedula) {
        return usuarioRepositorio.buscarCliente(cedula);
    }

    public Usuario iniciarSesion(String email, String contrasenia) throws Exception {
        if (email == null || email.isEmpty()) throw new Exception("El email es obligatorio");
        if (contrasenia == null || contrasenia.isEmpty()) throw  new Exception("la contrasenia es obligatoria");
        return usuarioRepositorio.iniciarSesion(email, contrasenia);
    }
}
