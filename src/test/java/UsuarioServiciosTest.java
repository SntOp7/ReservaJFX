import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.NotificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.UsuarioServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioServiciosTest {

    private UsuarioRepositorio usuarioRepositorio;
    private NotificacionServicios notificacionServicios;
    private AlojamientoServicios alojamientoServicios;
    private UsuarioServicios usuarioServicios;

    @BeforeEach
    void setUp() {
        usuarioRepositorio = mock(UsuarioRepositorio.class);
        notificacionServicios = mock(NotificacionServicios.class);
        alojamientoServicios = mock(AlojamientoServicios.class);

        usuarioServicios = new UsuarioServicios(notificacionServicios, alojamientoServicios);
    }

    @Test
    void activarCuentaCliente_codigoIngresadoNulo_lanzaExcepcion() {
        Exception ex = assertThrows(Exception.class, () ->
                usuarioServicios.activarCuentaCliente("1234567890", "CODIGO", null)
        );
        assertEquals("El codigo es obligatorio", ex.getMessage());
    }

    @Test
    void activarCuentaCliente_clienteNoExiste_lanzaExcepcion() {
        when(usuarioRepositorio.buscarCliente("1234567890")).thenReturn(null);

        Exception ex = assertThrows(Exception.class, () ->
                usuarioServicios.activarCuentaCliente("1234567890", "CODIGO", "CODIGO")
        );
        assertEquals("El cliente no existe", ex.getMessage());
    }

}
