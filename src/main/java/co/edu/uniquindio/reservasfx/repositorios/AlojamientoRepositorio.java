package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class AlojamientoRepositorio {
    public ArrayList<Alojamiento> alojamientos;

    public AlojamientoRepositorio() {
        alojamientos = new ArrayList<>();
    }

    public void agregar(Alojamiento alojamiento) {
        alojamientos.add(alojamiento);
    }

    public void editar(Alojamiento alojamiento) {
        alojamientos.set(alojamientos.indexOf(alojamiento), alojamiento);
    }

    public void eliminar(Alojamiento alojamiento) {
        alojamientos.remove(alojamiento);
    }

    public Alojamiento buscarAlojamientoPorNombre(String nombre) {
        return alojamientos.stream()
                .filter(a -> a.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    public Alojamiento buscarAlojamientoPorId(String id) {
        return alojamientos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
