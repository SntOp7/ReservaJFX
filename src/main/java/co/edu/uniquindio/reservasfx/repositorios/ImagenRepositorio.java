package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Imagen;

import java.util.ArrayList;

public class ImagenRepositorio {
    private ArrayList<Imagen> imagenes;

    public ImagenRepositorio() {
        imagenes = new ArrayList<>();
    }

    public void agregar(Imagen imagen) {
        imagenes.add(imagen);
    }
}
