package co.edu.uniquindio.reservasfx.modelo.factory;

import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;

import java.util.ArrayList;

public class AlojamientoFactory {

    public static Casa crearCasa(String id, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                                 int capacidadMaxima, String imagenPrincipal, double costoAseoYMantenimiento) {
        return Casa.builder()
                .id(id)
                .nombre(nombre)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .precioPorNoche(precioPorNoche)
                .capacidadMaxima(capacidadMaxima)
                .costoAseoYMantenimiento(costoAseoYMantenimiento)
                .imagenPrincipal(imagenPrincipal)
                .build();
    }

    public static Alojamiento crearApartamento(String id, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                                               int capacidadMaxima, String imagenPrincipal, double costoAseoYMantenimiento) {
        return Apartamento.builder()
                .id(id)
                .nombre(nombre)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .precioPorNoche(precioPorNoche)
                .capacidadMaxima(capacidadMaxima)
                .costoAseoYMantenimiento(costoAseoYMantenimiento)
                .imagenPrincipal(imagenPrincipal)
                .build();
    }

    public static Alojamiento crearHotel(String id, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                                         int capacidadMaxima, String imagenPrincipal) {
        return Hotel.builder()
                .id(id)
                .nombre(nombre)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .precioPorNoche(precioPorNoche)
                .capacidadMaxima(capacidadMaxima)
                .imagenPrincipal(imagenPrincipal)
                .build();
    }
}
