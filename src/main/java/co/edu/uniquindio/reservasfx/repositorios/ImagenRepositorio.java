package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class ImagenRepositorio {
    private ArrayList<Imagen> imagenes;

    public ImagenRepositorio() {
        this.imagenes = leerDatos();
        System.out.println("Imagenes cargados desde archivo: " + imagenes.size());
    }

    public void agregar(Imagen imagen) {
        imagenes.add(imagen);
    }

    public void eliminar(Imagen imagen) {
        imagenes.remove(imagen);
    }

    public void eliminarImagenesAlojamiento(String idAlojamiento) {
        imagenes.removeIf(imagen -> imagen.getIdAlojamiento().equals(idAlojamiento));
    }

    public ArrayList<Imagen> obtenerImagenesAlojamientoPorId(String idAlojamiento) {
        ArrayList<Imagen> imagenesPorId = new ArrayList<>();
        for (Imagen imagen : imagenes) {
            if (imagen.getIdAlojamiento().equals(idAlojamiento)) {
                imagenesPorId.add(imagen);
            }
        }
        return imagenesPorId;
    }

    public void guardarDatos(ArrayList<Imagen> imagenes) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_IMAGENES, imagenes);
            System.out.println("Imagenes guardados en archivo: " + imagenes.size());
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
