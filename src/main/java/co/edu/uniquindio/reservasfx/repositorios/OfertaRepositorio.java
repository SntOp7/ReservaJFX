package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoOferta;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
public class OfertaRepositorio {
    private ArrayList<Oferta> ofertas;

    public OfertaRepositorio() {
        this.ofertas = leerDatos();
        System.out.println("Ofertas cargados desde archivo: " + ofertas.size());
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

    public Oferta buscarOfertaPorId(String id){
        return ofertas.stream().
                filter(o -> o.getId().equals(id)).
                findFirst().
                orElse(null);
    }

    public ArrayList<Oferta> obtenerOfertasAlojamiento(String idAlojamiento) {
        ArrayList<Oferta> ofertasPorAlojamiento = new ArrayList<>();
        for (Oferta oferta : ofertas) {
            if (oferta.getIdAlojamiento().equals(idAlojamiento)) {
                ofertasPorAlojamiento.add(oferta);
            }
        }
        return ofertasPorAlojamiento;
    }

    public ArrayList<Oferta> obtenerOfertasActivasAlojamiento(String idAlojamiento) {
        ArrayList<Oferta> ofertasPorAlojamiento = new ArrayList<>();
        for (Oferta oferta : ofertas) {
            if (oferta.getIdAlojamiento().equals(idAlojamiento) && oferta.getEstado().equals(EstadoOferta.ACTIVA)) {
                ofertasPorAlojamiento.add(oferta);
            }
        }
        return ofertasPorAlojamiento;
    }

    public void actualizarEstadoOfertas() {
        for (Oferta oferta : ofertas) {
            if (oferta.getFechaInicio().isEqual(LocalDate.now()) || oferta.getFechaInicio().isAfter(LocalDate.now())) {
                oferta.setEstado(EstadoOferta.ACTIVA);
            } else if (LocalDate.now().isAfter(oferta.getFechaFin())) {
                oferta.setEstado(EstadoOferta.CADUCADA);
            }
        }
    }

    public void guardarDatos(ArrayList<Oferta> ofertas) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_OFERTAS, ofertas);
            System.out.println("Ofertas guardados en archivo: " + ofertas.size());
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
