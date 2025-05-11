package co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.HabitacionRepositorio;
import co.edu.uniquindio.reservasfx.servicios.ModuloAlojamientoServicios;

public class HabitacionServicios  {

    private final HabitacionRepositorio habitacionRepositorio;

    public HabitacionServicios() {
        habitacionRepositorio = new HabitacionRepositorio();
    }

    public void registrarHabitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception {
        if (idHotel == null || idHotel.isEmpty()) throw new Exception("El id es obligatorio");
        if (numero == 0) throw new Exception("El numero es obligatorio");
        if (precio == 0) throw new Exception("El precio es obligatorio");
        if (capacidad == 0) throw new Exception("La capacidad es obligatoria");
        if (descripcion == null || descripcion.isEmpty()) throw new Exception("La descripcion es obligatoria");
        if (imagen == null || imagen.isEmpty()) throw new Exception("La imagen es obligatoria");

        Habitacion habitacion = buscarHabitacion(idHotel, numero);
        if (habitacion == null) throw new Exception("La habitacion ya existe");
        if (buscarHabitacion(idHotel, numero) != null) throw new Exception("Una Habitacion ya existe con ese numero");
        habitacionRepositorio.agregar(habitacion);
    }

    public void editarHabitacion(String idHotel, int numero, double nuevoPrecio, int nuevaCapacidad, String imagen, String descipcion) throws Exception {
        Habitacion habitacionExistente = buscarHabitacion(idHotel, numero);

        if (habitacionExistente == null) {
            throw new Exception("Habitación no encontrada");
        }

        Habitacion habitacionEditada = Habitacion.builder()
                .numero(numero)
                .idHotel(idHotel)
                .capacidad(nuevaCapacidad)
                .precio(nuevoPrecio)
                .imagen(imagen)
                .descripcion(descipcion)
                .build();

        habitacionRepositorio.editar(habitacionEditada);
    }

    public void eliminarHabitacion(String idHotel, int numero) throws Exception {
        Habitacion habitacion = buscarHabitacion(idHotel, numero);
        if (habitacion == null) throw new Exception("La Habitacion no existe");
        habitacionRepositorio.eliminar(habitacion);

    }

    public  Habitacion buscarHabitacion(String idHotel, int numero) throws Exception {
        return habitacionRepositorio.buscarHabitacion(idHotel, numero);
    }
}
