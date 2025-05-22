package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class DeseoRepositorio {

    private ArrayList<Deseo> deseos;

    public DeseoRepositorio() {
        this.deseos = leerDatos();
        System.out.println("Deseos cargados desde archivo: " + deseos.size());
    }

    public void agregar(Deseo deseo) {
        deseos.add(deseo);
    }

    public void eliminar(Deseo deseo) {
        deseos.remove(deseo);
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
                deseosPorCedula.add(deseo);
            }
        }
        return deseosPorCedula;
    }

    public void guardarDatos(ArrayList<Deseo> deseos) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_DESEOS, deseos);
            System.out.println("Deseos guardados en archivo: " + deseos.size());
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
