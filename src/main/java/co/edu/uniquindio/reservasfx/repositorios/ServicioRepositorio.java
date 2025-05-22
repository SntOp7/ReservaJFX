package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class ServicioRepositorio {
    private ArrayList<Servicio> servicios;

    public ServicioRepositorio() {
        this.servicios = leerDatos();
        System.out.println("Servicios cargados desde archivo: " + servicios.size());
    }

    public void agregar(Servicio servicio) {
        servicios.add(servicio);
    }

    public void eliminar(Servicio servicio) {
        servicios.remove(servicio);
    }

    public void eliminarServiciosAlojamiento(String idAlojamiento) {
        servicios.removeIf(servicio -> servicio.getIdAlojamiento().equals(idAlojamiento));
    }

    public ArrayList<Servicio> obtenerServiciosAlojamientoPorId(String idAlojamiento) {
        ArrayList<Servicio> serviciosPorId = new ArrayList<>();
        for (Servicio servicio : servicios) {
            if (servicio.getIdAlojamiento().equals(idAlojamiento)) {
                serviciosPorId.add(servicio);
            }
        }
        return serviciosPorId;
    }

    public void guardarDatos(ArrayList<Servicio> servicios) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_SERVICIOS, servicios);
            System.out.println("Servicios cargados desde archivo: " + servicios.size());
        } catch (IOException e) {
            System.err.println("Error guardando servicios: " + e.getMessage());
        }
    }


    public ArrayList<Servicio> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_SERVICIOS);
            if (datos != null) {
                return (ArrayList<Servicio>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando servicios: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
