package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.util.ArrayList;

public class ImagenRepositorio {
    private ArrayList<Imagen> imagenes;

    public ImagenRepositorio() {
        imagenes = new ArrayList<>();
    }

    public void agregar(Imagen imagen) {
        imagenes.add(imagen);
    }

    public void editar(Imagen imagen) {
        imagenes.set(imagenes.indexOf(imagen), imagen);
    }

    public void eliminar(Imagen imagen) {
        imagenes.remove(imagen);
    }

    public void eliminarImagenesAlojamiento(String idAlojamiento) {
        for (Imagen imagen : imagenes) {
            if (imagen.getIdAlojamiento().equals(idAlojamiento)) {
                eliminar(imagen);
            }
        }
    }
}
