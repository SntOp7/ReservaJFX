package co.edu.uniquindio.reservasfx.repositorios;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Factura;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.utils.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

@Getter
public class ReservaRepositorio {
    private ArrayList<Reserva> reservas;

    public ReservaRepositorio() {
        this.reservas = leerDatos();
        System.out.println("Reservas cargados desde archivo: " + reservas.size());
        //listarReservas();
    }

    public void agregar(Reserva reserva) {
        reservas.add(reserva);
    }

    public void cancelar(Reserva reserva) {
        reserva.setEstado(EstadoReserva.CANCELADA);
    }

    private void listarReservas() {
        LocalDate inicio1 = LocalDate.of(2025, 6, 10);
        LocalDate fin1 = LocalDate.of(2025, 6, 13);

        LocalDate inicio2 = LocalDate.of(2025, 7, 5);
        LocalDate fin2 = LocalDate.of(2025, 7, 7);

        LocalDate inicio3 = LocalDate.of(2025, 8, 1);
        LocalDate fin3 = LocalDate.of(2025, 8, 4);

        double subtotal1 = ChronoUnit.DAYS.between(inicio1, fin1) * 150000 + 50000;
        double total1 = subtotal1;

        Factura factura1 = Factura.builder()
                .id(UUID.randomUUID().toString())
                .subtotal(subtotal1)
                .total(total1)
                .fecha(LocalDateTime.now())
                .codigoQR("QR-CASA-001")
                .build();

        Reserva reserva1 = Reserva.builder()
                .id(UUID.randomUUID().toString())
                .cedulaCliente("1030280881")
                .idAlojamiento("CASA-005")
                .fechaInicio(inicio1)
                .fechaFin(fin1)
                .numeroHuespedes(5)
                .factura(factura1)
                .estado(EstadoReserva.FINALIZADA)
                .build();

        reservas.add(reserva1);

        double subtotal2 = ChronoUnit.DAYS.between(inicio2, fin2) * 90000;
        double total2 = subtotal2;

        Factura factura2 = Factura.builder()
                .id(UUID.randomUUID().toString())
                .subtotal(subtotal2)
                .total(total2)
                .fecha(LocalDateTime.now())
                .codigoQR("QR-HOTEL-001")
                .build();

        Reserva reserva2 = Reserva.builder()
                .id(UUID.randomUUID().toString())
                .cedulaCliente("1094898051")
                .idAlojamiento("HOTEL-005")
                .fechaInicio(inicio2)
                .fechaFin(fin2)
                .numeroHuespedes(2)
                .factura(factura2)
                .estado(EstadoReserva.FINALIZADA)
                .build();

        reservas.add(reserva2);

        double dias3 = ChronoUnit.DAYS.between(inicio3, fin3);
        double subtotal3 = dias3 * 60000 + 30000;
        double total3 = subtotal3 - (subtotal3 * 0.10); // 10% descuento

        Factura factura3 = Factura.builder()
                .id(UUID.randomUUID().toString())
                .subtotal(subtotal3)
                .total(total3)
                .fecha(LocalDateTime.now())
                .codigoQR("QR-APT-001")
                .build();

        Reserva reserva3 = Reserva.builder()
                .id(UUID.randomUUID().toString())
                .cedulaCliente("1090274779")
                .idAlojamiento("APT-005")
                .fechaInicio(inicio3)
                .fechaFin(fin3)
                .numeroHuespedes(2)
                .factura(factura3)
                .estado(EstadoReserva.FINALIZADA)
                .build();

        reservas.add(reserva3);
    }

    public Reserva buscarReservaPorId(String id) {
        return reservas.stream().
                filter(r -> r.getId().equals(id)).
                findFirst().
                orElse(null);
    }

    public ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) {
        ArrayList<Reserva> reservasPorCliente = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getCedulaCliente().equals(cedulaCliente)) {
                reservasPorCliente.add(reserva);
            }
        }
        return reservasPorCliente;
    }

    public ArrayList<Reserva> obtenerReservasPorAlojamiento(String idAlojamiento) {
        ArrayList<Reserva> reservasPorAlojamiento = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getIdAlojamiento().equals(idAlojamiento)) {
                reservasPorAlojamiento.add(reserva);
            }
        }
        return reservasPorAlojamiento;
    }

    public void actualizarEstadoReservas() {
        for (Reserva reserva : reservas) {
            if (LocalDate.now().isAfter(reserva.getFechaFin())) {
                reserva.setEstado(EstadoReserva.FINALIZADA);
            }
        }
    }

    public void guardarDatos(ArrayList<Reserva> reservas) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_RESERVAS, reservas);
            System.out.println("Reservas guardados en archivo: " + reservas.size());
        } catch (IOException e) {
            System.err.println("Error guardando reservas: " + e.getMessage());
        }
    }


    public ArrayList<Reserva> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_RESERVAS);
            if (datos != null) {
                return (ArrayList<Reserva>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando reservas: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
