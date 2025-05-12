package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;

import java.util.ArrayList;

public class DeseoRepositorio {

    private ArrayList<Deseo> deseos;

    public DeseoRepositorio() {
        deseos = new ArrayList<>();
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
                deseos.add(deseo);
            }
        }
        return deseosPorCedula;
    }
}
