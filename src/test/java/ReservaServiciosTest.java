import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.modelo.factory.Hotel;
import co.edu.uniquindio.reservasfx.repositorios.ReservaRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.HabitacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.OfertaServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.ReservaServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

public class ReservaServiciosTest {

    @Mock
    private HabitacionServicios habitacionServicios;

    @InjectMocks
    private ReservaServicios servicio;  // Clase que tiene el método verificarCapacidadAlojamiento

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalcularSubtotal_Hotel() {
        Hotel hotel = new Hotel();
        hotel.setPrecioPorNoche(100.0);

        LocalDate inicio = LocalDate.of(2025, 6, 1);
        LocalDate fin = LocalDate.of(2025, 6, 6); // 5 días

        double subtotal = servicio.calcularSubtotal(hotel, inicio, fin);

        assertEquals(500.0, subtotal);
    }

    @Test
    void testCalcularSubtotal_Casa() {
        Casa casa = new Casa();
        casa.setPrecioPorNoche(150.0);
        casa.setCostoAseoYMantenimiento(100.0);

        LocalDate inicio = LocalDate.of(2025, 6, 1);
        LocalDate fin = LocalDate.of(2025, 6, 4); // 3 días

        double subtotal = servicio.calcularSubtotal(casa, inicio, fin);

        // 3 * 150 + 100 = 550
        assertEquals(550.0, subtotal);
    }

    @Test
    void testCalcularSubtotal_Apartamento() {
        Apartamento apto = new Apartamento();
        apto.setPrecioPorNoche(200.0);
        apto.setCostoAseoYMantenimiento(80.0);

        LocalDate inicio = LocalDate.of(2025, 6, 10);
        LocalDate fin = LocalDate.of(2025, 6, 15); // 5 días

        double subtotal = servicio.calcularSubtotal(apto, inicio, fin);

        // 5 * 200 + 80 = 1080
        assertEquals(1080.0, subtotal);
    }

}
