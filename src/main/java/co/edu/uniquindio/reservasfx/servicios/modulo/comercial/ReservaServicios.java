package co.edu.uniquindio.reservasfx.servicios.modulo.comercial;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Habitacion;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Factura;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.EstadoReserva;
import co.edu.uniquindio.reservasfx.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.factory.Apartamento;
import co.edu.uniquindio.reservasfx.modelo.factory.Casa;
import co.edu.uniquindio.reservasfx.modelo.factory.Hotel;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;
import co.edu.uniquindio.reservasfx.repositorios.ReservaRepositorio;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IAlojamiento;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.HabitacionServicios;
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
    private final HabitacionServicios habitacionServicios;
    private final NotificacionServicios notificacionServicios;

    public ReservaServicios(UsuarioServicios usuarioServicios, AlojamientoServicios alojamientoServicios,
                            HabitacionServicios habitacionServicios, NotificacionServicios notificacionServicios) {
        reservaRepositorio = new ReservaRepositorio();
        this.usuarioServicios = usuarioServicios;
        this.alojamientoServicios = alojamientoServicios;
        this.habitacionServicios = habitacionServicios;
        this.notificacionServicios = notificacionServicios;
    }

    public void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                                String numeroHuespedes, ArrayList<Oferta> ofertasAlojamiento, int numeroHabitacion) throws Exception {

        if (fechaInicio == null) throw new Exception("La fecha de inicio es obligatoria");
        if (fechaFin == null) throw new Exception("La fecha de fin es obligatoria");
        if (fechaInicio.isBefore(LocalDate.now())) throw new Exception("La fecha de inicio no puede ser en el pasado");
        if (fechaFin.isBefore(fechaInicio)) throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio");
        if (numeroHuespedes ==null || numeroHuespedes.isEmpty()) throw new Exception("El número de huéspedes es obligatorio");

        int huespedes = Integer.parseInt(numeroHuespedes);
        if (huespedes <= 0) throw new  Exception("El mumero de huespedes debe ser mayor a 0");

        Alojamiento alojamiento = alojamientoServicios.buscarAlojamientoPorId(idAlojamiento);
        if (alojamiento instanceof Hotel) {
            if (numeroHabitacion == 0) throw new Exception("Debes seleccionar una habitación para realizar la reserva");
        }
        if (!verificarCapacidadAlojamiento(alojamiento, huespedes, numeroHabitacion))
            throw new Exception("El alojamiento no dispone de la capacidad solicitada");
        Reserva reservaConflicto = obtenerReservaConflicto(alojamiento.getId(), fechaInicio, fechaFin);
        if (reservaConflicto != null) {
            LocalDate fechaDisponible = reservaConflicto.getFechaFin().plusDays(1);
            throw new Exception("El alojamiento ya está reservado en las fechas seleccionadas. Estará disponible a partir del " + fechaDisponible);
        }

        Cliente cliente = usuarioServicios.buscarClientePorCedula(cedulaCliente);

        double subtotal = calcularSubtotal(alojamiento, fechaInicio, fechaFin);
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
                .numeroHuespedes(huespedes)
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

    private boolean verificarCapacidadAlojamiento(Alojamiento alojamiento, int numeroHuespedes,
                                                  int numeroHabitacion) throws Exception {
        boolean capacidadDisponible = false;
        if (alojamiento instanceof Hotel) {
            Habitacion habitacion = habitacionServicios.buscarHabitacion(alojamiento.getId(), numeroHabitacion);
            capacidadDisponible = habitacion.getCapacidad() >= numeroHuespedes;
        } else if (alojamiento instanceof Casa) {
            capacidadDisponible = alojamiento.getCapacidadMaxima() >= numeroHuespedes;
        } else if (alojamiento instanceof Apartamento) {
            capacidadDisponible = alojamiento.getCapacidadMaxima() >= numeroHuespedes;
        }
        return capacidadDisponible;
    }

    public double calcularSubtotal(Alojamiento alojamiento, LocalDate inicio, LocalDate fin) {
        long dias = ChronoUnit.DAYS.between(inicio, fin);
        double subtotal = 0;
        if (alojamiento instanceof Hotel) {
            subtotal = dias * alojamiento.getPrecioPorNoche();
        } else if (alojamiento instanceof Casa) {
            subtotal = dias * alojamiento.getPrecioPorNoche() + ((Casa)alojamiento).getCostoAseoYMantenimiento();
        } else if (alojamiento instanceof Apartamento) {
            subtotal = dias * alojamiento.getPrecioPorNoche() + ((Apartamento) alojamiento).getCostoAseoYMantenimiento();
        }
        return subtotal;
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

    public ArrayList<EstadisticasTipoAlojamiento> obtenerRentabilidadTipoAlojamiento(int mes) {
        EstadisticasTipoAlojamiento estadisticasCasa = new EstadisticasTipoAlojamiento();
        EstadisticasTipoAlojamiento estadisticasApartamento = new EstadisticasTipoAlojamiento();
        EstadisticasTipoAlojamiento estadisticasHotel = new EstadisticasTipoAlojamiento();
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
        estadisticasCasa.setTipo(TipoAlojamiento.CASA);
        estadisticasApartamento.setTipo(TipoAlojamiento.APARTAMENTO);
        estadisticasHotel.setTipo(TipoAlojamiento.HOTEL);

        estadisticasCasa.setRentabilidad(totalCasa);
        estadisticasApartamento.setRentabilidad(totalApartamento);
        estadisticasHotel.setRentabilidad(totalHotel);

        ArrayList<EstadisticasTipoAlojamiento> estadisticas = new ArrayList<>();
        estadisticas.add(estadisticasCasa);
        estadisticas.add(estadisticasApartamento);
        estadisticas.add(estadisticasHotel);
        return estadisticas;
    }

    public void actualizarEstadoReservas() {
        reservaRepositorio.actualizarEstadoReservas();
    }
}
