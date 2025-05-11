package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;

import java.util.ArrayList;

public class ServicioRepositorio {
    private ArrayList<Servicio> servicios;

    public ServicioRepositorio() {
        servicios = new ArrayList<>();
    }

    public void agregar(Servicio servicio) {
        servicios.add(servicio);
    }
}
