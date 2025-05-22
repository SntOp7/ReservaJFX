package co.edu.uniquindio.reservasfx.config;

import co.edu.uniquindio.reservasfx.modelo.entidades.reserva.Factura;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Cliente;
import co.edu.uniquindio.reservasfx.modelo.factory.Alojamiento;

import java.time.LocalDate;

public class Constantes {
    public static String RESERVA_EXITOSA(String nombreAlojamiento) {
        return "Tu reserva en " + nombreAlojamiento + " ha sido confirmada.";
    }

    public static String RESERVA_CANCELADA_POR_CLIENTE(String nombreAlojamiento) {
        return "Has cancelado tu reserva en " + nombreAlojamiento + ".";
    }

    public static String CONFIRMACION_PAGO(String nombreAlojamiento, double monto) {
        return "Se descontaron $" + monto + " de tu billetera por la reserva en " + nombreAlojamiento + ".";
    }

    public static String QR_GENERADO(String codigoFactura) {
        return "Tu código QR para la factura " + codigoFactura + " ya está disponible en tu correo.";
    }

    public static String CALIFICACION_PUBLICADA(String nombreAlojamiento) {
        return "Tu reseña sobre " + nombreAlojamiento + " fue publicada. ¡Gracias por tu opinión!";
    }

    public static String NUEVA_OFERTA(String nombreAlojamiento) {
        return "¡" + nombreAlojamiento + " tiene una nueva oferta! Consulta el descuento disponible.";
    }

    public static String ACTUALIZACION_DATOS_CLIENTE() {
        return "Tus datos han sido actualizados correctamente.";
    }

    public static String CAMBIO_CONTRASENA() {
        return "Tu contraseña fue actualizada correctamente.";
    }

    public static String CODIGO_VERIFICACION() {
        return "Se ha enviado el código de verificación para actualizar tu contraseña a tu correo.";
    }

    public static String DESEO_AGREGADO(String nombreAlojamiento) {
        return "Has agregado " + nombreAlojamiento + " a tu lista de deseos.";
    }

    public static String DESEO_REMOVIDO(String nombreAlojamiento) {
        return "Has eliminado " + nombreAlojamiento + " de tu lista de deseos.";
    }

    public static String ENVIO_CODIGO(String codigo, String nombre) {
        return "Hola " + nombre + "\n" +
                "\n" +
                "Hemos recibido una solicitud en BookYourStay.\n" +
                "\n" +
                "Tu código de verificación es: " + codigo + "\n" +
                "\n" +
                "Ingresa este código en la plataforma para completar tu proceso. \n" +
                "Este código es confidencial. No lo compartas con nadie.\n" +
                "\n" +
                "Si tú no realizaste esta solicitud, puedes ignorar este mensaje.\n" +
                "\n" +
                "Gracias por confiar en nosotros,\n" +
                "El equipo de BookYourStay";
    }

    public static String ENVIO_DETALLES_RESERVA(Cliente cliente, Factura factura, Alojamiento alojamiento,
                                                LocalDate fechaInicio, LocalDate fechaFin) {
        return "<p>Estimado/a " + cliente.getNombre() + ",</p>" +
                "<p>Gracias por realizar tu reserva en BookYourStay.</p>" +
                "<p><strong>Detalles de tu reserva:</strong></p>" +
                "<ul>" +
                "<li><strong>Factura:</strong> " + factura.getId() + "</li>" +
                "<li><strong>Alojamiento:</strong> " + alojamiento.getNombre() + "</li>" +
                "<li><strong>Fecha de ingreso:</strong> " + fechaInicio + "</li>" +
                "<li><strong>Fecha de salida:</strong> " + fechaFin + "</li>" +
                "<li><strong>Total pagado:</strong> $" + factura.getTotal() + "</li>" +
                "</ul>" +
                "<p>Tu código QR está incluido a continuación, junto con tu código de factura.</p>";
    }

    public static final String RUTA_ALOJAMIENTOS = "data/alojamientos.data";

    public static final String RUTA_CALIFICACIONES = "data/calificaciones.data";

    public static final String RUTA_DESEOS = "data/deseos.data";

    public static final String RUTA_HABITACIONES = "data/habitaciones.data";

    public static final String RUTA_IMAGENES = "data/imagenes.data";

    public static final String RUTA_NOTIFICACIONES = "data/notificaciones.data";

    public static final String RUTA_OFERTAS = "data/ofertas.data";

    public static final String RUTA_RESERVAS = "data/reservas.data";

    public static final String RUTA_SERVICIOS = "data/servicios.data";

    public static final String RUTA_CLIENTES = "data/clientes.data";

    public static final String RUTA_ADMINISTRADORES  = "data/administradores.data";
}
