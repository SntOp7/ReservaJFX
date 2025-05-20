import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.edu.uniquindio.reservasfx.repositorios.NotificacionRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.NotificacionServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class NotificacionServiciosTest {
    private NotificacionRepositorio notificacionRepositorio;
    private NotificacionServicios notificacionService;

    @BeforeEach
    void setUp() {
        notificacionRepositorio = mock(NotificacionRepositorio.class);
        notificacionService = new NotificacionServicios();
    }

    @Test
    void testEnviarNotificacion_TituloNulo_LanzaExcepcion() {
        Exception ex = assertThrows(Exception.class, () ->
                notificacionService.enviarNotificacion("123", null, "Mensaje")
        );
        assertEquals("El titulo es obligatorio", ex.getMessage());
    }

    @Test
    void testEnviarNotificacion_MensajeVacio_LanzaExcepcion() {
        Exception ex = assertThrows(Exception.class, () ->
                notificacionService.enviarNotificacion("123", "Titulo", "")
        );
        assertEquals("El mensaje es obligatorio", ex.getMessage());
    }


}
