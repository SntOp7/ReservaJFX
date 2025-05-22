package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Administrador;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Usuario;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class UsuarioRepositorio {
    private ArrayList<Cliente> clientes;
    private ArrayList<Administrador> administradores;

    public UsuarioRepositorio() {
        this.clientes = leerDatosCl();
        this.administradores = leerDatosAd();
        listarCliente();
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

    public void listarCliente() {
        Cliente cliente1 = new Cliente("1030280881", "Juan Hernandez", "3175560537",
                "Calarca - Quindio","juanse13hg@gmail.com", "juan", true);
        clientes.add(cliente1);
        Cliente cliente2 = new Cliente("1094898051", "Sharif Giraldo", "3104553300",
                "Calarca - Quindio","sharifgiraldo23@gmail.com", "sharif", true);
        clientes.add(cliente2);
        Cliente cliente3 = new Cliente("1090274779", "Santiago Ospina", "3023696218",
                "Armenia - Quindio","santiospi2022@gmail.com", "santiago", true);
        clientes.add(cliente3);
    }

    public void listarAdministrador() {
        Administrador admin1 = new Administrador("123456789", "AdminApp",
                "987654321", "Calarca - Quindio","hernandezguevarajuansebastian@gmail.com",
                "admin", true);
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

    public void guardarDatosCl(ArrayList<Cliente> clientes) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_CLIENTES, clientes);
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }
    }


    public ArrayList<Cliente> leerDatosCl() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_CLIENTES);
            if (datos != null) {
                return (ArrayList<Cliente>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando clientes: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void guardarDatosAd(ArrayList<Administrador> administradores) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_ADMINISTRADORES, administradores);
        } catch (IOException e) {
            System.err.println("Error guardando administradores: " + e.getMessage());
        }
    }


    public ArrayList<Administrador> leerDatosAd() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_ADMINISTRADORES);
            if (datos != null) {
                return (ArrayList<Administrador>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando administradores: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
