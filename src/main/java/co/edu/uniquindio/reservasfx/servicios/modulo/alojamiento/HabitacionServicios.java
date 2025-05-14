package co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.HabitacionRepositorio;
import co.edu.uniquindio.reservasfx.servicios.ModuloAlojamientoServicios;

import java.util.ArrayList;

public class HabitacionServicios  {

    private final HabitacionRepositorio habitacionRepositorio;

    public HabitacionServicios() {
        habitacionRepositorio = new HabitacionRepositorio();
    }

    public void registrarHabitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception {
        if (idHotel == null || idHotel.isEmpty()) throw new Exception("El id es obligatorio");
        if (numero == 0) throw new Exception("El numero es obligatorio");
        verificarCampos(precio, capacidad, descripcion, imagen);

        if (buscarHabitacion(idHotel, numero) != null) throw new Exception("Una Habitacion ya existe con ese numero");

        Habitacion habitacion = Habitacion.builder().numero(numero).idHotel(idHotel).precio(precio).capacidad(capacidad)
                .descripcion(descripcion).imagen(imagen).build();
        habitacionRepositorio.agregar(habitacion);
    }

    public void editarHabitacion(String idHotel, int numero, double precio, int capacidad, String descripcion, String imagen) throws Exception {
        verificarCampos(precio, capacidad, descripcion, imagen);
        Habitacion habitacion = buscarHabitacion(idHotel, numero);
        habitacion.setPrecio(precio);
        habitacion.setCapacidad(capacidad);
        habitacion.setDescripcion(descripcion);
        habitacion.setImagen(imagen);
        habitacionRepositorio.editar(habitacion);
    }

    private void verificarCampos(double precio, int capacidad, String descripcion, String imagen) throws Exception {
        if (precio <= 0) throw new Exception("El precio debe ser mayor a 0");
        if (capacidad <= 0) throw new Exception("La capacidad debe ser mayor a 0");
        if (descripcion == null || descripcion.isEmpty()) throw new Exception("La descripcion es obligatoria");
        if (imagen == null || imagen.isEmpty()) throw new Exception("La imagen es obligatoria");
    }

    public void eliminarHabitacion(String idHotel, int numero) throws Exception {
        Habitacion habitacion = buscarHabitacion(idHotel, numero);
        if (habitacion == null) throw new Exception("La Habitacion no existe");
        habitacionRepositorio.eliminar(habitacion);
    }

    public Habitacion buscarHabitacion(String idHotel, int numero) throws Exception {
        return habitacionRepositorio.buscarHabitacion(idHotel, numero);
    }

    public ArrayList<Habitacion> obtenerHabitacionesHotel(String idHotel) {
        return habitacionRepositorio.obtenerHabitacionesPorIdHotel(idHotel);
    }
}
