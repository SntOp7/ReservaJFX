package co.edu.uniquindio.reservasfx.config;

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

    public static String SOLICITUD_CALIFICACION(String nombreAlojamiento) {
        return "¿Cómo fue tu experiencia en " + nombreAlojamiento + "? Déjanos tu reseña.";
    }

    public static String CALIFICACION_PUBLICADA(String nombreAlojamiento) {
        return "Tu reseña sobre " + nombreAlojamiento + " fue publicada. ¡Gracias por tu opinión!";
    }

    public static String NUEVA_OFERTA(String nombreAlojamiento) {
        return "¡" + nombreAlojamiento + " tiene una nueva oferta! Consulta el descuento disponible.";
    }

    public static String DESCUENTO_EN_CIUDAD(String ciudad, int porcentaje) {
        return "Viaja de nuevo a " + ciudad + " con un descuento del " + porcentaje + "%";
    }

    public static String CAMBIO_CONTRASENA() {
        return "Tu contraseña fue actualizada correctamente.";
    }

    public static String CODIGO_VERIFICACION(String codigo) {
        return "Se ha enviado el código de verificación para actualizar tu contraseña a tu correo.";
    }

    public static String DISPONIBILIDAD_FAVORITO(String nombreAlojamiento) {
        return "¡" + nombreAlojamiento + " está disponible de nuevo! No pierdas la oportunidad.";
    }

    public static String RECORDATORIO_RESERVA(String nombreAlojamiento) {
        return "Tu estancia en " + nombreAlojamiento + " comienza en 24 horas. ¡Te esperamos!";
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
}
