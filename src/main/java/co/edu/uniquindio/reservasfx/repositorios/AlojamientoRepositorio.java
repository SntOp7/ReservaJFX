package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.Mes;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.modelo.factory.Hotel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

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


    public ArrayList<Alojamiento> obtenerAlojamientosAleatorios() {
        ArrayList<Alojamiento> copia = new ArrayList<>(alojamientos);
        Collections.shuffle(copia);
        return copia;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosPopulares(Ciudad ciudad, LinkedList<Reserva> reservas) {
        Map<Alojamiento, Integer> contadorReservas = new HashMap<>();

        for (Reserva reserva : reservas) {
            Alojamiento alojamiento = buscarAlojamientoPorId(reserva.getIdAlojamiento());
            if (alojamiento.getCiudad().equals(ciudad)) {
                contadorReservas.put(alojamiento, contadorReservas.getOrDefault(alojamiento, 0) + 1);
            }
        }

        ArrayList<Alojamiento> alojamientosPopulares = new ArrayList<>(contadorReservas.keySet());
        alojamientosPopulares.sort((a1, a2) -> contadorReservas.get(a2) - contadorReservas.get(a1));
        return alojamientosPopulares;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosOfertados(LinkedList<Oferta> ofertas) {
        ArrayList<Alojamiento> alojamientosConOferta = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        for (Oferta oferta : ofertas) {
            if ((oferta.getFechaInicio().isBefore(hoy) || oferta.getFechaInicio().isEqual(hoy)) &&
                    (oferta.getFechaFin().isAfter(hoy) || oferta.getFechaFin().isEqual(hoy))) {

                Alojamiento alojamiento = buscarAlojamientoPorId(oferta.getIdAlojamiento());
                if (!alojamientosConOferta.contains(alojamiento)) {
                    alojamientosConOferta.add(alojamiento);
                }
            }
        }
        return alojamientosConOferta;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(LinkedList<Reserva> reservasCliente) {
        if (reservasCliente.isEmpty()) return null;

        Set<TipoAlojamiento> tiposPreferidos = new HashSet<>();
        Set<Ciudad> ciudadesPreferidas = new HashSet<>();

        for (Reserva reserva : reservasCliente) {
            Alojamiento alojamiento = buscarAlojamientoPorId(reserva.getIdAlojamiento());
            TipoAlojamiento tipo = obtenerTipoAlojamiento(alojamiento);
            if (tipo != null) {
                tiposPreferidos.add(tipo);
            }
            ciudadesPreferidas.add(alojamiento.getCiudad());
        }

        ArrayList<Alojamiento> recomendados = new ArrayList<>();
        for (Alojamiento alojamiento : alojamientos) {
            TipoAlojamiento tipo = obtenerTipoAlojamiento(alojamiento);
            if ((tipo != null && tiposPreferidos.contains(tipo)) ||
                    ciudadesPreferidas.contains(alojamiento.getCiudad())) {
                recomendados.add(alojamiento);
            }
        }
        return recomendados;
    }

    private TipoAlojamiento obtenerTipoAlojamiento(Alojamiento alojamiento) {
        TipoAlojamiento tipoAlojamiento = null;
        if (alojamiento instanceof Casa) {
            tipoAlojamiento = TipoAlojamiento.CASA;
        }
        if (alojamiento instanceof Apartamento) {
            tipoAlojamiento = TipoAlojamiento.APARTAMENTO;
        }
        if (alojamiento instanceof Hotel) {
            tipoAlojamiento = TipoAlojamiento.HOTEL;
        }
        return tipoAlojamiento;
    }

    public ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(LinkedList<Deseo> deseosCliente) {
        if (deseosCliente.isEmpty()) return null;

        ArrayList<Alojamiento> alojamientosDeseados = new ArrayList<>();

        for (Deseo deseo : deseosCliente) {
            Alojamiento alojamiento = buscarAlojamientoPorId(deseo.getIdAlojamiento());
            if (!alojamientosDeseados.contains(alojamiento)) {
                alojamientosDeseados.add(alojamiento);
            }
        }
        return alojamientosDeseados;
    }
}
