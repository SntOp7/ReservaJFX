package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.util.ArrayList;

public class ServicioRepositorio {
    private ArrayList<Servicio> servicios;

    public ServicioRepositorio() {
        servicios = new ArrayList<>();
    }

    public void agregar(Servicio servicio) {
        servicios.add(servicio);
    }

    public void editar(Servicio servicio) {
        servicios.set(servicios.indexOf(servicio), servicio);
    }

    public void eliminar(Servicio servicio) {
        servicios.remove(servicio);
    }

    public void eliminarServiciosAlojamiento(String idAlojamiento) {
        for (Servicio servicio : servicios) {
            if (servicio.getIdAlojamiento().equals(idAlojamiento)) {
                eliminar(servicio);
            }
        }
    }
}
