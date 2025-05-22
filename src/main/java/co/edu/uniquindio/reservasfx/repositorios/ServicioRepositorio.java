package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.utils.Persistencia;

import java.io.IOException;
import java.util.ArrayList;

public class ServicioRepositorio {
    private ArrayList<Servicio> servicios;

    public ServicioRepositorio() {
        this.servicios = leerDatos();
    }

    public void agregar(Servicio servicio) {
        servicios.add(servicio);
        //guardarDatos(servicios);
    }

    public void eliminar(Servicio servicio) {
        servicios.remove(servicio);
        //guardarDatos(servicios);
    }

    public void eliminarServiciosAlojamiento(String idAlojamiento) {
        servicios.removeIf(servicio -> servicio.getIdAlojamiento().equals(idAlojamiento));
        //guardarDatos(servicios);
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
