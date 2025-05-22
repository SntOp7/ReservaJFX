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
        System.out.println("Alojamientos cargados desde archivo: " + alojamientos.size());
        //listarAlojamientos();
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
                .imagenPrincipal("@img/apt_loft.png")
                .build());

        alojamientos.add(Hotel.builder()
                .id("HOTEL-001")
                .nombre("Hotel Boutique Andino")
                .precioPorNoche(320)
                .capacidadMaxima(2)
                .ciudad(Ciudad.MEDELLIN)
                .imagenPrincipal("@img/hotel_andino.png")
                .build());

        alojamientos.add(Casa.builder()
                .id("CASA-002")
                .nombre("Casa Familiar Las Palmas")
                .precioPorNoche(220)
                .capacidadMaxima(5)
                .ciudad(Ciudad.CARTAGENA)
                .imagenPrincipal("@img/casa_palmas.png")
                .build());

        alojamientos.add(Hotel.builder()
                .id("HOTEL-002")
                .nombre("Hotel Playa Dorada")
                .precioPorNoche(400)
                .capacidadMaxima(3)
                .ciudad(Ciudad.SANTA_MARTA)
                .imagenPrincipal("@img/hotel_playa.png")
                .build());

        alojamientos.add(Apartamento.builder()
                .id("APT-002")
                .nombre("Apartamento Vista al Mar")
                .precioPorNoche(200)
                .capacidadMaxima(2)
                .ciudad(Ciudad.CARTAGENA)
                .imagenPrincipal("@img/apt_mar.png")
                .build());

        alojamientos.add(Casa.builder()
                .id("CASA-003")
                .nombre("Casa Colonial del Centro")
                .precioPorNoche(270)
                .capacidadMaxima(6)
                .ciudad(Ciudad.BOGOTA)
                .imagenPrincipal("@img/casa_colonial.png")
                .build());

        alojamientos.add(Hotel.builder()
                .id("HOTEL-003")
                .nombre("Hotel Nevado Deluxe")
                .precioPorNoche(380)
                .capacidadMaxima(4)
                .ciudad(Ciudad.MANIZALES)
                .imagenPrincipal("@img/hotel_nevado.png")
                .build());

        alojamientos.add(Apartamento.builder()
                .id("APT-003")
                .nombre("Apartamento Urbano 22")
                .precioPorNoche(190)
                .capacidadMaxima(3)
                .ciudad(Ciudad.MEDELLIN)
                .imagenPrincipal("@img/apt_urbano.png")
                .build());

        alojamientos.add(Casa.builder()
                .id("CASA-004")
                .nombre("Casa Tranquila del Bosque")
                .precioPorNoche(210)
                .capacidadMaxima(5)
                .ciudad(Ciudad.PEREIRA)
                .imagenPrincipal("@img/casa_bosque.png")
                .build());

        alojamientos.add(Hotel.builder()
                .id("HOTEL-004")
                .nombre("Hotel Jardines del Valle")
                .precioPorNoche(350)
                .capacidadMaxima(2)
                .ciudad(Ciudad.ARMENIA)
                .imagenPrincipal("@img/hotel_jardines.png")
                .build());

        alojamientos.add(Apartamento.builder()
                .id("APT-004")
                .nombre("Apartamento Torre 9")
                .precioPorNoche(175)
                .capacidadMaxima(2)
                .ciudad(Ciudad.CALI)
                .imagenPrincipal("@img/apt_torre.png")
                .build());

        alojamientos.add(Casa.builder()
                .id("CASA-005")
                .nombre("Casa del Río")
                .precioPorNoche(280)
                .capacidadMaxima(6)
                .ciudad(Ciudad.MANIZALES)
                .imagenPrincipal("@img/casa_rio.png")
                .build());

        alojamientos.add(Hotel.builder()
                .id("HOTEL-005")
                .nombre("Hotel El Descanso")
                .precioPorNoche(290)
                .capacidadMaxima(3)
                .ciudad(Ciudad.SANTA_MARTA)
                .imagenPrincipal("@img/hotel_descanso.png")
                .build());

        alojamientos.add(Apartamento.builder()
                .id("APT-005")
                .nombre("Apartamento Parque Norte")
                .precioPorNoche(160)
                .capacidadMaxima(2)
                .ciudad(Ciudad.MEDELLIN)
                .imagenPrincipal("@img/apt_parque.png")
                .build());

        alojamientos.add(Casa.builder()
                .id("CASA-006")
                .nombre("Casa Moderna Sabaneta")
                .precioPorNoche(310)
                .capacidadMaxima(5)
                .ciudad(Ciudad.MEDELLIN)
                .imagenPrincipal("@img/casa_mod.png")
                .build());

        alojamientos.add(Hotel.builder()
                .id("HOTEL-006")
                .nombre("Hotel Colonial del Café")
                .precioPorNoche(330)
                .capacidadMaxima(4)
                .ciudad(Ciudad.PEREIRA)
                .imagenPrincipal("@img/hotel_cafe.png")
                .build());

        alojamientos.add(Apartamento.builder()
                .id("APT-006")
                .nombre("Apartamento Alto Prado")
                .precioPorNoche(210)
                .capacidadMaxima(3)
                .ciudad(Ciudad.BARRANQUILLA)
                .imagenPrincipal("@img/apt_prado.png")
                .build());

        alojamientos.add(Casa.builder()
                .id("CASA-007")
                .nombre("Casa Verde Tropical")
                .precioPorNoche(260)
                .capacidadMaxima(6)
                .ciudad(Ciudad.SANTA_MARTA)
                .imagenPrincipal("@img/casa_verde.png")
                .build());

        alojamientos.add(Hotel.builder()
                .id("HOTEL-007")
                .nombre("Hotel Bahía Real")
                .precioPorNoche(370)
                .capacidadMaxima(2)
                .ciudad(Ciudad.CARTAGENA)
                .imagenPrincipal("@img/hotel_bahia.png")
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
                .filter(a -> {
                    if (tipoAlojamiento == TipoAlojamiento.CASA) return a instanceof Casa;
                    if (tipoAlojamiento == TipoAlojamiento.APARTAMENTO) return a instanceof Apartamento;
                    if (tipoAlojamiento == TipoAlojamiento.HOTEL) return a instanceof Hotel;
                    return false;
                })
                .filter(a -> nombre == null || nombre.isBlank() || a.getNombre().toLowerCase().startsWith(nombre.toLowerCase()))
                .filter(a -> ciudad == null || ciudad.isBlank() || a.getCiudad().getNombre().equalsIgnoreCase(ciudad))
                .filter(a -> minPrecio <= 0 || a.getPrecioPorNoche() >= minPrecio)
                .filter(a -> maxPrecio <= 0 || a.getPrecioPorNoche() <= maxPrecio)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void guardarDatos(ArrayList<Alojamiento> alojamientos) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_ALOJAMIENTOS, alojamientos);
            System.out.println("Alojamientos guardados en archivo: " + alojamientos.size());
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
