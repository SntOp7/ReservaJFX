package co.edu.uniquindio.reservasfx.modelo.factory;

import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Hotel extends Alojamiento {

    public static class HotelBuilder {
        private final Hotel hotel = new Hotel();

        public HotelBuilder id(String id) {
            hotel.setId(id);
            return this;
        }

        public HotelBuilder nombre(String nombre) {
            hotel.setNombre(nombre);
            return this;
        }

        public HotelBuilder ciudad(Ciudad ciudad) {
            hotel.setCiudad(ciudad);
            return this;
        }

        public HotelBuilder descripcion(String descripcion) {
            hotel.setDescripcion(descripcion);
            return this;
        }

        public HotelBuilder precioPorNoche(double precio) {
            hotel.setPrecioPorNoche(precio);
            return this;
        }

        public HotelBuilder capacidadMaxima(int capacidad) {
            hotel.setCapacidadMaxima(capacidad);
            return this;
        }

        public HotelBuilder imagenPrincipal(String imagen) {
            hotel.setImagenPrincipal(imagen);
            return this;
        }

        public Hotel build() {
            return hotel;
        }
    }

    public static HotelBuilder builder() {
        return new HotelBuilder();
    }
}
