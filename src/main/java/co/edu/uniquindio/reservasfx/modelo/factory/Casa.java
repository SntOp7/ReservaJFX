package co.edu.uniquindio.reservasfx.modelo.factory;

import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
public class Casa extends Alojamiento implements Serializable {
    private double costoAseoYMantenimiento;

    public static class CasaBuilder {
        private final Casa casa = new Casa();

        public CasaBuilder id(String id) {
            casa.setId(id);
            return this;
        }

        public CasaBuilder nombre(String nombre) {
            casa.setNombre(nombre);
            return this;
        }

        public CasaBuilder ciudad(Ciudad ciudad) {
            casa.setCiudad(ciudad);
            return this;
        }

        public CasaBuilder descripcion(String descripcion) {
            casa.setDescripcion(descripcion);
            return this;
        }

        public CasaBuilder precioPorNoche(double precio) {
            casa.setPrecioPorNoche(precio);
            return this;
        }

        public CasaBuilder capacidadMaxima(int capacidad) {
            casa.setCapacidadMaxima(capacidad);
            return this;
        }

        public CasaBuilder costoAseoYMantenimiento(double costo) {
            casa.setCostoAseoYMantenimiento(costo);
            return this;
        }

        public CasaBuilder imagenPrincipal(String imagen) {
            casa.setImagenPrincipal(imagen);
            return this;
        }

        public Casa build() {
            return casa;
        }
    }

    public static CasaBuilder builder() {
        return new CasaBuilder();
    }
}
