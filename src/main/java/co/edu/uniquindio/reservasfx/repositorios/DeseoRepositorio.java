package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.utils.Persistencia;

import java.io.IOException;
import java.util.ArrayList;

public class DeseoRepositorio {

    private ArrayList<Deseo> deseos;

    public DeseoRepositorio() {
        this.deseos = leerDatos();
    }

    public void agregar(Deseo deseo) {
        deseos.add(deseo);
        guardarDatos(deseos);
    }

    public void eliminar(Deseo deseo) {
        deseos.remove(deseo);
        guardarDatos(deseos);
    }

    public Deseo buscarDeseo(String cedula, String idAlojamiento) {
        return deseos.stream().
                filter(e -> e.getCedulaCliente().equals(cedula) && e.getIdAlojamiento().equals(idAlojamiento)).
                findFirst().
                orElse(null);
    }

    public ArrayList<Deseo> obtenerDeseosPorCedula(String cedulaCliente) {
        ArrayList<Deseo> deseosPorCedula = new ArrayList<>();
        for (Deseo deseo : deseos) {
            if (deseo.getCedulaCliente().equals(cedulaCliente)) {
                deseos.add(deseo);
            }
        }
        return deseosPorCedula;
    }

    public void guardarDatos(ArrayList<Deseo> deseos) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_DESEOS, deseos);
        } catch (IOException e) {
            System.err.println("Error guardando deseos: " + e.getMessage());
        }
    }


    public ArrayList<Deseo> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_DESEOS);
            if (datos != null) {
                return (ArrayList<Deseo>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando deseos: " + e.getMessage());
        }
        return new ArrayList<>();
    }

}
