package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.utils.Persistencia;

import java.io.IOException;
import java.util.ArrayList;

public class ImagenRepositorio {
    private ArrayList<Imagen> imagenes;

    public ImagenRepositorio() {
        this.imagenes = leerDatos();
    }

    public void agregar(Imagen imagen) {
        imagenes.add(imagen);
        //guardarDatos(imagenes);
    }

    public void editar(Imagen imagen) {
        imagenes.set(imagenes.indexOf(imagen), imagen);
        //guardarDatos(imagenes);
    }

    public void eliminar(Imagen imagen) {
        imagenes.remove(imagen);
        //guardarDatos(imagenes);
    }

    public void eliminarImagenesAlojamiento(String idAlojamiento) {
        for (Imagen imagen : imagenes) {
            if (imagen.getIdAlojamiento().equals(idAlojamiento)) {
                eliminar(imagen);
            }
        }
        //guardarDatos(imagenes);
    }

    public void guardarDatos(ArrayList<Imagen> imagenes) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_IMAGENES, imagenes);
        } catch (IOException e) {
            System.err.println("Error guardando imagenes: " + e.getMessage());
        }
    }


    public ArrayList<Imagen> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_IMAGENES);
            if (datos != null) {
                return (ArrayList<Imagen>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagenes: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
