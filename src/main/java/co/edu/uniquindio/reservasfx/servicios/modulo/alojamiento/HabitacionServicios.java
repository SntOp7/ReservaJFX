package co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.repositorios.HabitacionRepositorio;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class HabitacionServicios  {

    private final HabitacionRepositorio habitacionRepositorio;

    public HabitacionServicios() {
        habitacionRepositorio = new HabitacionRepositorio();
    }

    public Habitacion crearHabitacion(String numeroString, String precioString, String capacidadString, String descripcion,
                                    String imagen) throws Exception {

        verificarCampos(numeroString, precioString, capacidadString, descripcion, imagen);
        int numero = 0;
        int capacidad = 0;
        double precio = 0;
        try {
            numero = Integer.parseInt(numeroString);
            capacidad = Integer.parseInt(capacidadString);
            precio = Double.parseDouble(precioString);
        } catch (NumberFormatException e) {
            throw new Exception("Los campos de precio, capacidad y numero de habitacion deben ser números");
        }
        verificarNumeros(numero, capacidad, precio);

        return Habitacion.builder().numero(numero).precio(precio).capacidad(capacidad)
                .descripcion(descripcion).imagen(imagen).build();
    }

    public void registrarHabitacion(String idHotel, Habitacion habitacion) throws Exception {
        if (buscarHabitacion(idHotel, habitacion.getNumero()) != null) throw new Exception("Hay una Habitacion repetida con ese numero");
        habitacion.setIdHotel(idHotel);
        habitacionRepositorio.agregar(habitacion);
    }

    public Habitacion verificarEdicionHabitacion(Habitacion habitacionAntigua, String numeroString, String precioString,
                                           String capacidadString, String descripcion, String imagen,
                                                 ArrayList<Habitacion> habitaciones) throws Exception {

        verificarCampos(numeroString, precioString, capacidadString, descripcion, imagen);
        int numero = 0;
        int capacidad = 0;
        double precio = 0;
        try {
            numero = Integer.parseInt(numeroString);
            capacidad = Integer.parseInt(capacidadString);
            precio = Double.parseDouble(precioString);
        } catch (NumberFormatException e) {
            throw new Exception("Los campos de precio, capacidad y numero de habitacion deben ser números");
        }
        for (Habitacion h : habitaciones) {
            if (!h.equals(habitacionAntigua) && h.getNumero() == numero) {
                throw new Exception("Ya existe una habitación con el número " + numero);
            }
        }
        verificarNumeros(numero, capacidad, precio);
        habitacionAntigua.setNumero(numero);
        habitacionAntigua.setPrecio(precio);
        habitacionAntigua.setCapacidad(capacidad);
        habitacionAntigua.setDescripcion(descripcion);
        habitacionAntigua.setImagen(imagen);
        return habitacionAntigua;
    }

    public void editarHabitacion(String idHotel, Habitacion habitacion) throws Exception {
        if (buscarHabitacion(idHotel, habitacion.getNumero()) != null) throw new Exception("Hay una Habitacion repetida con ese numero");
        habitacionRepositorio.editar(habitacion);
    }

    private void verificarCampos(String numero, String precio, String capacidad, String descripcion, String imagen) throws Exception {
        if (numero == null || numero.isEmpty()) throw new Exception("El numero es obligatorio");
        if (precio == null || precio.isEmpty()) throw new Exception("El precio es obligatorio");
        if (capacidad == null || capacidad.isEmpty()) throw new Exception("La capacidad es obligatoria");
        if (descripcion == null || descripcion.isEmpty()) throw new Exception("La descripcion es obligatoria");
        if (imagen == null || imagen.isEmpty()) throw new Exception("La imagen es obligatoria");
    }

    private void verificarNumeros(int numero, int capacidad, double precio) {
        if (numero <= 0) throw new IllegalArgumentException("El numero debe ser mayor a 0");
        if (capacidad <= 0) throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser mayor a 0");
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
