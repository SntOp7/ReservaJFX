import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.CalificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.NotificacionServicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CalificacionServiciosTest {

    private CalificacionServicios calificacionServicios;

    @BeforeEach
    void setUp() {
        // No se usan en este test
        calificacionServicios = new CalificacionServicios(null, null);
    }

    @Test
    void testReservaFinalizadaExistente() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        reservas.add(
                Reserva.builder()
                        .idAlojamiento("ALOJ001")
                        .estado(EstadoReserva.FINALIZADA)
                        .build()
        );

        boolean resultado = calificacionServicios.clienteTuvoReservaCompletadaEnAlojamiento(reservas, "ALOJ001");
        assertTrue(resultado);
    }

    @Test
    void testReservaNoFinalizada() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        reservas.add(
                Reserva.builder()
                        .idAlojamiento("ALOJ001")
                        .estado(EstadoReserva.ACTIVA)
                        .build()
        );

        boolean resultado = calificacionServicios.clienteTuvoReservaCompletadaEnAlojamiento(reservas, "ALOJ001");
        assertFalse(resultado);
    }

    @Test
    void testReservaConOtroAlojamiento() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        reservas.add(
                Reserva.builder()
                        .idAlojamiento("ALOJ999")
                        .estado(EstadoReserva.FINALIZADA)
                        .build()
        );

        boolean resultado = calificacionServicios.clienteTuvoReservaCompletadaEnAlojamiento(reservas, "ALOJ001");
        assertFalse(resultado);
    }

    @Test
    void testListaReservasVacia() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        boolean resultado = calificacionServicios.clienteTuvoReservaCompletadaEnAlojamiento(reservas, "ALOJ001");
        assertFalse(resultado);
    }
}
