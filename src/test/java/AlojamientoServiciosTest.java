
import co.edu.uniquindio.reservasfx.modelo.enums.Ciudad;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.repositorios.AlojamientoRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.ImagenRepositorio;
import co.edu.uniquindio.reservasfx.repositorios.ServicioRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlojamientoServiciosTest {

    private AlojamientoServicios alojamientoServicios;
    private AlojamientoRepositorio alojamientoRepositorioMock;

    @BeforeEach
    public void setup() {
        alojamientoRepositorioMock = mock(AlojamientoRepositorio.class);
        ServicioRepositorio servicioRepositorioMock = mock(ServicioRepositorio.class);
        ImagenRepositorio imagenRepositorioMock = mock(ImagenRepositorio.class);

        alojamientoServicios = new AlojamientoServicios(
                alojamientoRepositorioMock,
                servicioRepositorioMock,
                imagenRepositorioMock
        );
    }

    @Test
    public void testRegistrarAlojamiento_CreaCasaCorrectamente() throws Exception {
        // Datos de entrada
        TipoAlojamiento tipo = TipoAlojamiento.CASA;
        String nombre = "Casa Playa";
        String ciudad = "MEDELLIN";
        String descripcion = "Hermosa casa cerca al mar";
        String precioPorNoche = "200000";
        String capacidad = "5";
        ArrayList<TipoServicio> servicios = new ArrayList<>();
        servicios.add(TipoServicio.WIFI);
        String imagenPrincipal = "casa1.jpg";
        ArrayList<String> imagenes = new ArrayList<>();
        imagenes.add("casa2.jpg");
        String costoAseo = "50000";

        // Ejecutar el método
        Alojamiento alojamiento = alojamientoServicios.registrarAlojamiento(
                tipo, nombre, ciudad, descripcion,
                precioPorNoche, capacidad, servicios,
                imagenPrincipal, imagenes, costoAseo
        );

        // Verificaciones
        assertNotNull(alojamiento);
        assertEquals(nombre, alojamiento.getNombre());
        assertEquals(Ciudad.MEDELLIN, alojamiento.getCiudad());
        verify(alojamientoRepositorioMock).agregar(any(Alojamiento.class));
    }

    @Test
    public void testRegistrarAlojamiento_LanzaExcepcionSiCiudadVacia() {
        Exception exception = assertThrows(Exception.class, () -> {
            alojamientoServicios.registrarAlojamiento(
                    TipoAlojamiento.HOTEL, "Hotel Central", "", "Desc", "100000",
                    "2", new ArrayList<>(), "hotel.jpg", new ArrayList<>(), "0"
            );
        });

        assertEquals("La ciudad es obligatoria", exception.getMessage());
    }
}