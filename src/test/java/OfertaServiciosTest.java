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
    void testEditarOferta_CamposValidos_EdicionCorrecta() throws Exception {
        String id = "oferta123";
        String nombreNuevo = "Super Oferta";
        String descripcionNueva = "Descuento especial para verano";
        LocalDate fechaInicio = LocalDate.of(2025, 6, 1);
        LocalDate fechaFin = LocalDate.of(2025, 6, 30);
        String porcentajeDescuento = "20";

        Oferta ofertaOriginal = Oferta.builder()
                .id(id)
                .nombre("Oferta Vieja")
                .descripcion("Descripcion vieja")
                .fechaInicio(LocalDate.of(2025, 5, 1))
                .fechaFin(LocalDate.of(2025, 5, 31))
                .porcentajeDescuento(10.0)
                .estado(EstadoOferta.ACTIVA)
                .build();

        when(ofertaRepositorio.buscarOfertaPorId(id)).thenReturn(ofertaOriginal);

        ofertaServicio.editarOferta(id, nombreNuevo, descripcionNueva, fechaInicio, fechaFin, porcentajeDescuento);

        assertEquals(nombreNuevo, ofertaOriginal.getNombre());
        assertEquals(descripcionNueva, ofertaOriginal.getDescripcion());
        assertEquals(fechaInicio, ofertaOriginal.getFechaInicio());
        assertEquals(fechaFin, ofertaOriginal.getFechaFin());
        assertEquals(20.0, ofertaOriginal.getPorcentajeDescuento());

        verify(ofertaRepositorio).editar(ofertaOriginal);
    }
}
