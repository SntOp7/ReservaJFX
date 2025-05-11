package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Administrador;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;

@Getter
public class UsuarioRepositorio {
    private ArrayList<Cliente> clientes;
    private ArrayList<Administrador> administradores;

    public UsuarioRepositorio() {
        clientes = new ArrayList<>();
        administradores = new ArrayList<>();
        listarAdministrador();
    }

    public void agregar(Cliente cliente) {
        clientes.add(cliente);
    }

    public void editar(Cliente cliente) {
        clientes.set(clientes.indexOf(cliente), cliente);
    }

    public void eliminar(Cliente cliente) {
        clientes.remove(cliente);
    }

    public void listarAdministrador() {
        Administrador admin1 = new Administrador("123456789", "AdminApp", "987654321", "admin@gmail.com", "admin", true);
        administradores.add(admin1);
    }

    public Cliente buscarCliente(String cedula) {
        return clientes.stream().filter(cliente -> cliente.getCedula().equals(cedula)).findFirst().orElse(null);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        Usuario usuario = clientes.stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        if (usuario == null) {
            usuario = administradores.stream()
                    .filter(s -> s.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
        }
        return usuario;
    }
}
