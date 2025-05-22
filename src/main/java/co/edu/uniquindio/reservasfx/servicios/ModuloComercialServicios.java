package co.edu.uniquindio.reservasfx.servicios;

import co.edu.uniquindio.reservasfx.modelo.entidades.Oferta;
import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Reserva;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.enums.OfertaEspecial;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasAlojamiento;
import co.edu.uniquindio.reservasfx.modelo.vo.EstadisticasTipoAlojamiento;
import co.edu.uniquindio.reservasfx.servicios.interfaces.IComercial;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.AlojamientoServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento.HabitacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.OfertaServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.comercial.ReservaServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.NotificacionServicios;
import co.edu.uniquindio.reservasfx.servicios.modulo.usuario.UsuarioServicios;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
public class ModuloComercialServicios implements IComercial {

    private final ReservaServicios reservaServicios;
    private final OfertaServicios ofertaServicios;

    public ModuloComercialServicios(EmpresaServicio empresaServicio) {
        UsuarioServicios usuarioServicio = empresaServicio.getModuloUsuarioServicios().getUsuarioServicios();
        AlojamientoServicios alojamientoServicios = empresaServicio.getModuloAlojamientoServicios().getAlojamientoServicios();
        HabitacionServicios habitacionServicios = empresaServicio.getModuloAlojamientoServicios().getHabitacionServicios();
        NotificacionServicios notificacionServicios = empresaServicio.getModuloUsuarioServicios().getNotificacionServicios();
        reservaServicios = new ReservaServicios(usuarioServicio, alojamientoServicios, habitacionServicios, notificacionServicios);
        ofertaServicios = new OfertaServicios(usuarioServicio, notificacionServicios, alojamientoServicios);
    }

    @Override
    public void realizarReserva(String cedulaCliente, String idAlojamiento, LocalDate fechaInicio, LocalDate fechaFin,
                                String numeroHuespedes, int numeroHabitacion) throws Exception {
        reservaServicios.realizarReserva(cedulaCliente, idAlojamiento, fechaInicio, fechaFin, numeroHuespedes,
                obtenerOfertasAlojamiento(idAlojamiento), numeroHabitacion);
    }

    @Override
    public void cancelarReserva(String id) throws Exception {
        reservaServicios.cancelarReserva(id);
    }

    @Override
    public ArrayList<Reserva> obtenerReservasCliente(String cedulaCliente) throws Exception {
        return reservaServicios.obtenerReservasCliente(cedulaCliente);
    }

    @Override
    public void registrarOferta(String ofertaEspecial, String idAlojamiento, String nombre, String descripcion,
                                LocalDate fechaInicio, LocalDate fechaFin, String porcentajeDescuento) throws Exception {
        ofertaServicios.registrarOferta(ofertaEspecial, idAlojamiento, nombre, descripcion, fechaInicio, fechaFin,
                porcentajeDescuento);
    }

    @Override
    public void editarOferta(String id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                             String porcentajeDescuento) throws Exception {
        ofertaServicios.editarOferta(id, nombre, descripcion, fechaInicio, fechaFin, porcentajeDescuento);
    }

    @Override
    public void eliminarOferta(String id) throws Exception {
        ofertaServicios.eliminarOferta(id);
    }

    @Override
    public ArrayList<Oferta> obtenerOfertas() throws Exception {
        return ofertaServicios.obtenerTodasOfertas();
    }

    @Override
    public ArrayList<Oferta> obtenerOfertasAlojamiento(String idAlojamiento) throws Exception {
        return ofertaServicios.obtenerOfertas(idAlojamiento);
    }

    @Override
    public ArrayList<Oferta> obtenerOfertasActivasAlojamiento(String idAlojamiento) throws Exception {
        return ofertaServicios.obtenerOfertasActivasAlojamiento(idAlojamiento);
    }

    @Override
    public void actualizarEstadoReservas() throws Exception {
        reservaServicios.actualizarEstadoReservas();
    }

    @Override
    public void actualizarEstadoOfertas() throws Exception {
        ofertaServicios.actualizarEstadoOfertas();
    }

    @Override
    public EstadisticasAlojamiento obtenerEstadisticasAlojamiento(String idAlojamiento) throws Exception {
        return reservaServicios.obtenerEstadisticasAlojamiento(idAlojamiento);
    }

    @Override
    public ArrayList<EstadisticasTipoAlojamiento> obtenerRentabilidadTipoAlojamiento(int mes) throws Exception {
        return reservaServicios.obtenerRentabilidadTipoAlojamiento(mes);
    }
}
