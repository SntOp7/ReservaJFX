package co.edu.uniquindio.reservasfx.modelo.factory;

import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
public class Apartamento extends Alojamiento implements Serializable {
    private double costoAseoYMantenimiento;

    public static class ApartamentoBuilder {
        private final Apartamento apto = new Apartamento();

        public ApartamentoBuilder id(String id) {
            apto.setId(id);
            return this;
        }

        public ApartamentoBuilder nombre(String nombre) {
            apto.setNombre(nombre);
            return this;
        }

        public ApartamentoBuilder ciudad(Ciudad ciudad) {
            apto.setCiudad(ciudad);
            return this;
        }

        public ApartamentoBuilder descripcion(String descripcion) {
            apto.setDescripcion(descripcion);
            return this;
        }

        public ApartamentoBuilder precioPorNoche(double precio) {
            apto.setPrecioPorNoche(precio);
            return this;
        }

        public ApartamentoBuilder capacidadMaxima(int capacidad) {
            apto.setCapacidadMaxima(capacidad);
            return this;
        }

        public ApartamentoBuilder costoAseoYMantenimiento(double costo) {
            apto.setCostoAseoYMantenimiento(costo);
            return this;
        }

        public ApartamentoBuilder imagenPrincipal(String imagen) {
            apto.setImagenPrincipal(imagen);
            return this;
        }

        public Apartamento build() {
            return apto;
        }
    }

    public static ApartamentoBuilder builder() {
        return new ApartamentoBuilder();
    }
}
