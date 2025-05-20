import static org.junit.jupiter.api.Assertions.*;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.HabitacionServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HabitacionServiciosTest {
    private HabitacionServicios servicio;

    @BeforeEach
    public void setUp() {
        servicio = new HabitacionServicios();
    }

    @Test
    public void testCrearHabitacion_ConCamposValidos() throws Exception {
        // Datos válidos
        String numero = "101";
        String precio = "150.0";
        String capacidad = "2";
        String descripcion = "Habitación cómoda";
        String imagen = "imagen.jpg";

        Habitacion habitacion = servicio.crearHabitacion(numero, precio, capacidad, descripcion, imagen);

        assertNotNull(habitacion);
        assertEquals(101, habitacion.getNumero());
        assertEquals(150.0, habitacion.getPrecio());
        assertEquals(2, habitacion.getCapacidad());
        assertEquals("Habitación cómoda", habitacion.getDescripcion());
        assertEquals("imagen.jpg", habitacion.getImagen());
    }

    @Test
    public void testCrearHabitacion_ConNumeroNoValido_LanzaException() {
        assertThrows(Exception.class, () -> {
            servicio.crearHabitacion("abc", "150.0", "2", "Desc", "img.jpg");
        });
    }

    @Test
    public void testCrearHabitacion_ConPrecioNegativo_LanzaException() {
        assertThrows(Exception.class, () -> {
            servicio.crearHabitacion("101", "-150.0", "2", "Desc", "img.jpg");
        });
    }

    @Test
    public void testCrearHabitacion_ConCapacidadCero_LanzaException() {
        assertThrows(Exception.class, () -> {
            servicio.crearHabitacion("101", "150.0", "0", "Desc", "img.jpg");
        });
    }

    @Test
    public void testCrearHabitacion_ConDescripcionVacia_LanzaException() {
        assertThrows(Exception.class, () -> {
            servicio.crearHabitacion("101", "150.0", "2", "", "img.jpg");
        });
    }
}
