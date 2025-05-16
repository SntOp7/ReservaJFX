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
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import co.edu.uniquindio.reservasfx.config.Constantes;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class AlojamientoRepositorio {
    public ArrayList<Alojamiento> alojamientos;

    public AlojamientoRepositorio() {
        this.alojamientos = leerDatosAlojamiento();
        listarAlojamientos();
    }

    public void agregar(Alojamiento alojamiento) {
        alojamientos.add(alojamiento);
        //guardarDatos(alojamientos);

    }

    public void editar(Alojamiento alojamiento) {
        alojamientos.set(alojamientos.indexOf(alojamiento), alojamiento);
        //guardarDatos(alojamientos);

    }

    public void eliminar(Alojamiento alojamiento) {
        alojamientos.remove(alojamiento);
        //guardarDatos(alojamientos);

    }

    public void listarAlojamientos() {
        alojamientos.add(Casa.builder()
                        .id("CASA-001")
                        .nombre("Casa Campestre El Paraíso")
                        .precioPorNoche(250)
                        .capacidadMaxima(6)
                        .ciudad(Ciudad.ARMENIA)
                        .imagenPrincipal("@img/bookYourStay.png")
                        .build());

        alojamientos.add(Apartamento.builder()
                        .id("APT-001")
                        .nombre("Apartamento Central Loft")
                        .precioPorNoche(180)
                        .capacidadMaxima(4)
                        .ciudad(Ciudad.BOGOTA)
                        .build());

        alojamientos.add(Hotel.builder()
                        .id("HOTEL-001")
                        .nombre("Hotel Boutique Andino")
                        .precioPorNoche(320)
                        .capacidadMaxima(2)
                        .ciudad(Ciudad.MEDELLIN)
                        .build());

        alojamientos.add(Casa.builder()
                        .id("CASA-002")
                        .nombre("Casa Familiar Las Palmas")
                        .precioPorNoche(220)
                        .capacidadMaxima(5)
                        .ciudad(Ciudad.CARTAGENA)
                        .build());
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

    public ArrayList<Alojamiento> obtenerAlojamientosPopulares(Ciudad ciudad, ArrayList<Reserva> reservas) {
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

    public ArrayList<Alojamiento> obtenerAlojamientosOfertados(ArrayList<Oferta> ofertas) {
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

    public ArrayList<Alojamiento> obtenerAlojamientosPreferenciasCliente(ArrayList<Reserva> reservasCliente) {
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

    public ArrayList<Alojamiento> obtenerAlojamientosPorDeseosCliente(ArrayList<Deseo> deseosCliente) {
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

    public ArrayList<Alojamiento> obtenerAlojamientosPorFiltro(TipoAlojamiento tipoAlojamiento, String nombre, String ciudad, double minPrecio, double maxPrecio) {
        return alojamientos.stream()
                .filter(a -> a.getTipo().equals(tipoAlojamiento.getNombre().toLowerCase(Locale.ROOT)))
                .filter(a -> nombre == null || nombre.isBlank() || a.getNombre().toLowerCase().startsWith(nombre.toLowerCase()))
                .filter(a -> ciudad == null || ciudad.isBlank() || a.getCiudad().getNombre().equalsIgnoreCase(ciudad))
                .filter(a -> (minPrecio <= 0 || a.getPrecioPorNoche() >= minPrecio) &&
                        (maxPrecio <= 0 || a.getPrecioPorNoche() <= maxPrecio))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void guardarDatos(ArrayList<Alojamiento> alojamientos) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_ALOJAMIENTOS, alojamientos);
        } catch (IOException e) {
            System.err.println("Error guardando alojamientos: " + e.getMessage());
        }
    }


    public ArrayList<Alojamiento> leerDatosAlojamiento() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_ALOJAMIENTOS);
            if (datos != null) {
                return (ArrayList<Alojamiento>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando alojamientos: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
