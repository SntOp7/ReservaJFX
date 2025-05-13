package co.edu.uniquindio.reservasfx.servicios.modulo.comercial;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Factura;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.modelo.factory.Hotel;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;
import co.edu.uniquindio.reservasfx.repositorios.ReservaRepositorio;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.NotificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.UsuarioServicios;
import co.edu.uniquindio.reservasfx.utils.EnvioEmail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

public class ReservaServicios {

    private final ReservaRepositorio reservaRepositorio;
    private final UsuarioServicios usuarioServicios;
    private final AlojamientoServicios alojamientoServicios;
    private final NotificacionServicios notificacionServicios;

    public ReservaServicios(UsuarioServicios usuarioServicios, AlojamientoServicios alojamientoServicios,
                            NotificacionServicios notificacionServicios) {
        reservaRepositorio = new ReservaRepositorio();
        this.usuarioServicios = usuarioServicios;
        this.alojamientoServicios = alojamientoServicios;
        this.notificacionServicios = notificacionServicios;
    }

    public void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                                int numeroHuespedes, ArrayList<Oferta> ofertasAlojamiento) throws Exception {

        if (fechaInicio == null) throw new Exception("La fecha de inicio es obligatoria");
        if (fechaFin == null) throw new Exception("La fecha de fin es obligatoria");
        if (fechaInicio.isBefore(LocalDate.now())) throw new Exception("La fecha de inicio no puede ser en el pasado");
        if (fechaFin.isBefore(fechaInicio)) throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio");
        if (numeroHuespedes <= 0) throw new Exception("El número de huéspedes debe ser mayor a cero");

        Alojamiento alojamiento = alojamientoServicios.buscarAlojamientoPorId(idAlojamiento);

        if (numeroHuespedes > alojamiento.getCapacidadMaxima())
            throw new Exception("El número de huéspedes excede la capacidad máxima del alojamiento");

        Reserva reservaConflicto = obtenerReservaConflicto(alojamiento.getId(), fechaInicio, fechaFin);
        if (reservaConflicto != null) {
            LocalDate fechaDisponible = reservaConflicto.getFechaFin().plusDays(1);
            throw new Exception("El alojamiento ya está reservado en las fechas seleccionadas. Estará disponible a partir del " + fechaDisponible);
        }

        Cliente cliente = usuarioServicios.buscarClientePorCedula(cedulaCliente);

        double subtotal = calcularSubtotal(alojamiento.getPrecioPorNoche(), fechaInicio, fechaFin);
        double total = calcularTotalConOfertas(subtotal, ofertasAlojamiento);

        if (cliente.getBilletera().getSaldo() < total) {
            throw new Exception("El cliente no tiene saldo suficiente en su billetera para realizar la reserva");
        }

        String idFactura = UUID.randomUUID().toString();

        Factura factura = Factura.builder()
                .id(idFactura)
                .subtotal(subtotal)
                .total(total)
                .fecha(LocalDateTime.now())
                .codigoQR(idFactura)
                .build();

        Reserva reserva = Reserva.builder()
                .id(UUID.randomUUID().toString())
                .cedulaCliente(cedulaCliente)
                .idAlojamiento(idAlojamiento)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .numeroHuespedes(numeroHuespedes)
                .factura(factura)
                .estado(EstadoReserva.ACTIVA)
                .build();

        reservaRepositorio.agregar(reserva);

        String asunto = "Confirmación de Reserva - BookYourStay";
        String mensaje = Constantes.ENVIO_DETALLES_RESERVA(cliente, factura, alojamiento, fechaInicio, fechaFin);
        EnvioEmail.enviarNotificacionConQR(cliente.getEmail(), asunto, mensaje, idFactura);
        notificacionServicios.enviarNotificacion(cedulaCliente, "Reserva Exitosa",
                Constantes.RESERVA_EXITOSA(alojamiento.getNombre()));
        notificacionServicios.enviarNotificacion(cedulaCliente, "Confirmación de Pago",
                Constantes.CONFIRMACION_PAGO(alojamiento.getNombre(), total));
        notificacionServicios.enviarNotificacion(cedulaCliente, "Código QR Generado",
                Constantes.QR_GENERADO(idFactura));
    }

    private double calcularSubtotal(double precioPorNoche, LocalDate inicio, LocalDate fin) {
        long dias = ChronoUnit.DAYS.between(inicio, fin);
        return dias * precioPorNoche;
    }

    private double calcularTotalConOfertas(double subtotal, ArrayList<Oferta> ofertas) {
        double totalDescuento = 0;
        for (Oferta oferta : ofertas) {
            totalDescuento += subtotal * (oferta.getPorcentajeDescuento() / 100.0);
        }
        return subtotal - totalDescuento;
    }

    private Reserva obtenerReservaConflicto(String idAlojamiento, LocalDate inicio, LocalDate fin) {
        ArrayList<Reserva> reservas = reservaRepositorio.obtenerReservasPorAlojamiento(idAlojamiento);
        for (Reserva r : reservas) {
            if (r.getEstado() != EstadoReserva.CANCELADA && fechasSeTraslapan(r.getFechaInicio(), r.getFechaFin(), inicio, fin)) {
                return r;
            }
        }
        return null;
    }

    private boolean fechasSeTraslapan(LocalDate inicioExistente, LocalDate finExistente, LocalDate nuevoInicio, LocalDate nuevoFin) {
        return !(nuevoFin.isBefore(inicioExistente) || nuevoInicio.isAfter(finExistente));
    }

    public void cancelarReserva(String id) throws Exception {
        Reserva reserva = reservaRepositorio.buscarReservaPorId(id);
        if (reserva == null) throw new Exception("La reserva no existe");
        reservaRepositorio.cancelar(reserva);
        notificacionServicios.enviarNotificacion(reserva.getCedulaCliente(), "Reserva Cancelada",
                Constantes.RESERVA_CANCELADA_POR_CLIENTE(reserva.getIdAlojamiento()));
    }

    public ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) {
        return reservaRepositorio.obtenerReservasCliente(cedulaCliente);
    }

    public EstadisticasAlojamiento obtenerEstadisticasAlojamiento(String idAlojamiento) {
        EstadisticasAlojamiento estadisticas = new EstadisticasAlojamiento();
        Alojamiento alojamiento = alojamientoServicios.buscarAlojamientoPorId(idAlojamiento);
        if (alojamiento == null) return estadisticas;

        ArrayList<Reserva> reservas = reservaRepositorio.obtenerReservasPorAlojamiento(idAlojamiento);

        double gananciasTotales = 0;
        long diasOcupados = 0;
        LocalDate hoy = LocalDate.now();
        LocalDate inicioPeriodo = hoy.withDayOfYear(1); // desde el inicio del año
        long diasTotalesPeriodo = ChronoUnit.DAYS.between(inicioPeriodo, hoy.plusDays(1));

        for (Reserva reserva : reservas) {
            if (reserva.getEstado() != EstadoReserva.FINALIZADA) continue;

            long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
            diasOcupados += dias;
            gananciasTotales += dias * alojamiento.getPrecioPorNoche();
        }

        double ocupacionPorcentual = diasTotalesPeriodo > 0 ? (diasOcupados * 100.0) / diasTotalesPeriodo : 0;

        estadisticas.setGananciasTotales(gananciasTotales);
        estadisticas.setOcupacionPorcentual(ocupacionPorcentual);

        return estadisticas;
    }

    public EstadisticasTipoAlojamiento obtenerRentabilidadTipoAlojamiento(int mes) {
        EstadisticasTipoAlojamiento estadisticas = new EstadisticasTipoAlojamiento();
        ArrayList<Reserva> reservas = reservaRepositorio.getReservas();

        double totalCasa = 0;
        double totalApartamento = 0;
        double totalHotel = 0;

        for (Reserva reserva : reservas) {
            if (reserva.getEstado() != EstadoReserva.FINALIZADA) continue;
            if (reserva.getFechaInicio().getMonthValue() != mes) continue;

            Alojamiento alojamiento = alojamientoServicios.buscarAlojamientoPorId(reserva.getIdAlojamiento());
            if (alojamiento == null) continue;

            long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
            double ingreso = dias * alojamiento.getPrecioPorNoche();

            if (alojamiento instanceof Casa) {
                totalCasa += ingreso;
            } else if (alojamiento instanceof Apartamento) {
                totalApartamento += ingreso;
            } else if (alojamiento instanceof Hotel) {
                totalHotel += ingreso;
            }
        }

        estadisticas.setRentabilidadCasa(totalCasa);
        estadisticas.setRentabilidadApartamento(totalApartamento);
        estadisticas.setRentabilidadHotel(totalHotel);

        return estadisticas;
    }

    public void actualizarEstadoReservas() {
        reservaRepositorio.actualizarEstadoReservas();
    }
}
