package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.utils.Persistencia;

import java.io.IOException;
import java.util.ArrayList;

public class OfertaRepositorio {
    private ArrayList<Oferta> ofertas;

    public OfertaRepositorio() {
        this.ofertas = leerDatos();
    }

    public void agregar(Oferta oferta) {
        ofertas.add(oferta);
        guardarDatos(ofertas);
    }

    public void editar(Oferta oferta) {
        ofertas.set(ofertas.indexOf(oferta), oferta);
        guardarDatos(ofertas);
    }

    public void eliminar(Oferta oferta) {
        ofertas.remove(oferta);
        guardarDatos(ofertas);
    }

    public Oferta buscarOferta(String nombre){
        return ofertas.stream().filter(e -> e.getNombre().equals(nombre)).findFirst().orElse(null);
    }

    public void guardarDatos(ArrayList<Oferta> ofertas) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_OFERTAS, ofertas);
        } catch (IOException e) {
            System.err.println("Error guardando ofertas: " + e.getMessage());
        }
    }


    public ArrayList<Oferta> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_OFERTAS);
            if (datos != null) {
                return (ArrayList<Oferta>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando ofertas: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
