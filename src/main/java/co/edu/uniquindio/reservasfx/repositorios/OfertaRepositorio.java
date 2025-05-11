package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;

import java.util.ArrayList;

public class OfertaRepositorio {
    private ArrayList<Oferta> ofertas;

    public OfertaRepositorio() {
        ofertas = new ArrayList<>();
    }

    public void agregar(Oferta oferta) {
        ofertas.add(oferta);
    }

    public void editar(Oferta oferta) {
        ofertas.set(ofertas.indexOf(oferta), oferta);
    }

    public void eliminar(Oferta oferta) {
        ofertas.remove(oferta);
    }

    public Oferta buscarOferta(String nombre){
        return ofertas.stream().filter(e -> e.getNombre().equals(nombre)).findFirst().orElse(null);
    }
}
