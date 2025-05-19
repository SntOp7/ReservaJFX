import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoOferta;
import co.edu.uniquindio.reservasfx.repositorios.OfertaRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.OfertaServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class OfertaServiciosTest {

    @Mock
    private OfertaRepositorio ofertaRepositorio;

    @InjectMocks
    private OfertaServicios ofertaServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEditarOferta_OfertaNoExiste_LanzaExcepcion() {
        String id = "ofertaNoExiste";

        // El repositorio retorna null indicando que no existe la oferta
        when(ofertaRepositorio.buscarOfertaPorId(id)).thenReturn(null);

        // Verificamos que se lance la excepción al intentar editar una oferta inexistente
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ofertaServicio.editarOferta(id, "Nombre", "Descripcion", LocalDate.now(), LocalDate.now().plusDays(1), "10");
        });

        assertEquals("La oferta no existe", exception.getMessage());
    }
}
